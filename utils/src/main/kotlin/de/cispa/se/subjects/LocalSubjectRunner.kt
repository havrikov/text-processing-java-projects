package de.cispa.se.subjects

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.file
import mu.KotlinLogging
import de.cispa.se.subjects.coverage.CoverageExtractor
import de.cispa.se.subjects.coverage.CoverageReporter
import de.cispa.se.subjects.coverage.MethodReporter
import java.io.File

/** This class executes the subject in this process. */
class LocalSubjectRunner(private val executor: SubjectExecutor) : CliktCommand(name = "") {
    private val logger = KotlinLogging.logger {}

    private val ignoreExceptions by option(help = "Activate batch mode ignoring any exceptions thrown").flag()
    private val logExceptions by option(help = "Log thrown exceptions into the given file").file(canBeDir = false)

    private val reportCoverage by option(help = "Run with jacoco and report the achieved coverage into this csv file").file(canBeDir = false).validate {
        require(originalBytecode != null) { "When using --report-coverage you must also provide --original-bytecode!" }
    }
    private val reportMethods by option(help = "Run with jacoco and report the covered methods into this gzipped csv file").file(canBeDir = false).validate {
        require(originalBytecode != null) { "When using --report-methods you must also provide --original-bytecode!" }
    }

    private val originalBytecode by option(help = "Location of the original bytecode. Required only when run with --report-coverage and --report-methods").file(canBeDir = false, mustBeReadable = true)

    private val cumulative by option(help = "Do not reset the coverage information after each input").flag()

    private val inputs by argument(help = "Files or directories to feed to process", name = "<inputs>").file(mustExist = true, mustBeReadable = true).multiple(required = true)

    override fun run() {
        val inputFiles = inputs.gatherInputs()
        // by default just process each file
        var action: (File) -> Unit = executor::processFile
        var preEach = {}
        var postAll = {}

        // if requested, log exceptions
        logExceptions?.let {
            val exceptionLogger = ExceptionLogger(executor.packagePrefix, it)
            action = exceptionLogger.loggingExceptions(action)
            postAll = postAll.let { { it(); exceptionLogger.writeLog() } }
        }
        // if requested, ignore exceptions
        if (ignoreExceptions) action = ignoringExceptions(action)

        // if needed, set up a coverage extractor
        val extractor = if (reportCoverage != null || reportMethods != null) {
            CoverageExtractor(originalBytecode, executor.packagePrefix).also {
                if (!cumulative)
                    preEach = { it.reset() }
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
