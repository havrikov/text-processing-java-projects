package saarland.cispa.subjects

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import mu.KotlinLogging
import saarland.cispa.subjects.coverage.CoverageExtractor
import saarland.cispa.subjects.coverage.CoverageReporter
import saarland.cispa.subjects.coverage.MethodReporter
import java.io.File

/** Subject Drivers should subclass this and call processArgs in their main */
abstract class SubjectExecutor {
    private val logger = KotlinLogging.logger {}

    private class Settings(parser: ArgParser) {
        val ignoreExceptions by parser.flagging("Activate batch mode ignoring any exceptions thrown")
        val logExceptions by parser.storing("Log thrown exceptions into the given file", transform = ::File).default { null }
        val reportCoverage by parser.storing("Run with jacoco and report the achieved coverage into this csv file", transform = ::File).default { null }
        val reportMethods by parser.storing("Run with jacoco and report the covered methods into this gzipped csv file", transform = ::File).default { null }
        val originalBytecode by parser.storing("Location of the original bytecode. Required only when run with --report-coverage", transform = ::File).default { null }
        val inputs by parser.positionalList("Files or directories to feed to process", transform = ::File)
        val cumulative by parser.flagging("Do not reset the coverage information after each input.")
    }

    /** Subclasses should just call this method from their main */
    protected fun processArgs(args: Array<String>): Unit = mainBody {
        ArgParser(args).parseInto(::Settings).run {
            val inputFiles = inputs.gatherInputs()
            // by default just process each file
            var action: (File) -> Unit = ::processFile
            var preEach = {}
            var postAll = {}

            // if requested, log exceptions
            logExceptions?.let {
                val exceptionLogger = ExceptionLogger(packagePrefix, it)
                action = exceptionLogger.loggingExceptions(action)
                postAll = postAll.let { { it(); exceptionLogger.writeLog() } }
            }
            // if requested, ignore exceptions
            if (ignoreExceptions) action = ignoringExceptions(action)

            // if needed, set up a coverage extractor
            val extractor = if (reportCoverage != null || reportMethods != null) {
                CoverageExtractor(originalBytecode, packagePrefix).also {
                    if (!cumulative)
                        preEach = { it.getFresh = true }
                }
            } else null
            // if requested, also report the coverage counters by analyzing the original bytecode
            reportCoverage?.let {
                val reporter = CoverageReporter(it, extractor!!)
                action = reporter.recordingCoverage(action)
                postAll = postAll.let { { it(); reporter.close() } }
            }
            // if requested, report the covered methods
            reportMethods?.let {
                val reporter = MethodReporter(it, extractor!!)
                action = reporter.recordingCoverage(action)
                postAll = postAll.let { { it(); reporter.close() } }
            }

            try {
                inputFiles.forEach {
                    preEach()
                    action(it)
                }
            } finally {
                postAll()
            }
        }
    }

    /** This specifies the classes to be instrumented by jacoco */
    abstract val packagePrefix: String

    /** Override this method in the driver to process a file handle */
    open fun processFile(file: File): Unit = processInput(file.readText())

    /** Override this method in the driver to process the file text */
    open fun processInput(text: String): Unit = throw NotImplementedError("You forgot to override processInput or processFile in ${this.javaClass.simpleName}!")

    private inline fun ignoringExceptions(crossinline fileProcessor: (File) -> Unit): (File) -> Unit = { file ->
        try {
            fileProcessor(file)
        } catch (nc: NoClassDefFoundError) {
            logger.error(nc) { "Your test harness may be broken! Make sure to include all required dependencies in the classpath." }
            throw nc
        } catch (e: Throwable) {
            logger.error(e) { "Exception while processing file $file" }
        }
    }

    private fun List<File>.gatherInputs(): List<File> = flatMap { fileOrDirectory ->
        fileOrDirectory.walk().filter { it.isFile }.sorted().toList()
    }
}
