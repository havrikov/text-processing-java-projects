package saarland.cispa.subjects.coverage

import com.opencsv.CSVWriter
import org.jacoco.agent.rt.internal_43f5073.Agent
import org.jacoco.agent.rt.internal_43f5073.core.runtime.AgentOptions
import org.jacoco.core.analysis.Analyzer
import org.jacoco.core.analysis.CoverageBuilder
import org.jacoco.core.analysis.IClassCoverage
import org.jacoco.core.data.ExecutionDataReader
import org.jacoco.core.data.ExecutionDataStore
import java.io.File

class CoverageReporter(targetFile: File, private val originalByteCode: File?, packagePrefix: String) {

    private val coverages = listOf(ClassCoverageCounter, MethodCoverageCounter, LineCoverageCounter, InstructionCoverageCounter, BranchCoverageCounter)
    private val agent = Agent.getInstance(AgentOptions("output=none,dumponexit=false,excludes=*,includes=$packagePrefix*"))
    private val original: ByteArray
    private var fileCount = 0
    private val writer: CSVWriter

    init {
        require(originalByteCode != null && originalByteCode.exists()) { "The path to the original bytecode is invalid! Given: $originalByteCode" }
        original = originalByteCode.readBytes()
        // ensure the csv file can be written by creating its parent directory
        targetFile.absoluteFile.parentFile.mkdirs()
        // reset any coverage from startup if there is any
        agent.reset()
        writer = CSVWriter(targetFile.bufferedWriter())
        writer.writeNext(arrayOf("filenum", "filename", "class", "method", "line", "instruction", "branch"), false)
    }

    fun recordingCoverage(fileProcessor: (File) -> Unit): (File) -> Unit = { file ->
        try {
            fileProcessor(file)
        } finally {
            val execStore = getExecutionDataStore()
            val classes = getCoveredClasses(execStore)
            val list = listOf(fileCount.toString(), file.name) + coverages.map { it.compute(classes).toString() }.toMutableList()
            writer.writeNext(list.toTypedArray(), false)
            fileCount += 1
        }
    }

    fun close() = writer.close()

    private fun getCoveredClasses(execStore: ExecutionDataStore): Collection<IClassCoverage> {
        val coverageBuilder = CoverageBuilder()
        val analyzer = Analyzer(execStore, coverageBuilder)
        analyzer.analyzeAll(original.inputStream(), originalByteCode.toString())
        return coverageBuilder.classes
    }

    private fun getExecutionDataStore(): ExecutionDataStore {
        val executionData = agent.getExecutionData(false)
        val executionDataReader = ExecutionDataReader(executionData.inputStream())
        val execStore = ExecutionDataStore()
        executionDataReader.setExecutionDataVisitor(execStore)
        executionDataReader.setSessionInfoVisitor {} // must avoid NPE
        executionDataReader.read()
        return execStore
    }
}
