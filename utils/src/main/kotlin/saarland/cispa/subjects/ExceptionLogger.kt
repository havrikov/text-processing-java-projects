package saarland.cispa.subjects

import net.logstash.logback.stacktrace.StackHasher
import java.io.File
import java.util.concurrent.TimeoutException
import javax.json.Json

class ExceptionLogger(packagePrefix: String, private val outputFile: File) {
    private val subjectPackage = packagePrefix.replace('/', '.')

    private val exceptions = mutableMapOf<Throwable, MutableList<String>>()

    /**
     * Log exceptions only if they come from outside the subject itself.
     *
     * The rationale is that if the exception is declared in the subject,
     * then it is not a bug when it is thrown by the subject.
     */
    fun loggingExceptions(fileProcessor: (File) -> Unit): (File) -> Unit = { file ->
        try {
            fileProcessor(file)
        } catch (iae: IllegalArgumentException) {
            throw iae // this exception is usually thrown consciously so let's not count it as a potential bug
        } catch (tm: TimeoutException) {
            throw tm // this exception is probably unrelated to bugs
        } catch (t: Throwable) {
            val exceptionPackage = t.javaClass.`package`.name
            val commonPrefix = subjectPackage.commonPrefixWith(exceptionPackage)
            if (commonPrefix.length <= 4 || exceptionPackage.isEmpty()) { // the common prefix may at most be something like "com." or "org."
                // a call to fillInStackTrace ensures the correct stack trace is computed eagerly
                val ex = exceptions.getOrPut(t.fillInStackTrace()) { mutableListOf() }
                ex.add(file.name)
            }
            throw t
        }
    }

    fun writeLog() {
        if (exceptions.isNotEmpty()) {
            val arr = Json.createArrayBuilder()
            exceptions.forEach { (exception, files) ->
                arr.add(Json.createObjectBuilder().apply {
                    add("name", exception.javaClass.canonicalName)
                    add("location", exception.locationString())
                    add("stack_hash", exception.stackHash())
                    add("count", files.size)
                    add("files", Json.createArrayBuilder().apply { files.forEach { add(it) } })
                })
            }
            outputFile.writeText(arr.build().toString())
        }
    }

    private fun Throwable.locationString(): String {
        val trace = stackTrace
        return if (trace.isNullOrEmpty()) {
            "location unknown"
        } else {
            (trace.firstOrNull { it.className.startsWith(subjectPackage) } ?: trace[0]).let { "${it.className}:${it.lineNumber}" }
        }
    }

    companion object {
        private val stackHasher = StackHasher()
        private fun Throwable.stackHash() = stackHasher.hexHash(this)
    }
}
