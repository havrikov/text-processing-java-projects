package saarland.cispa.subjects.coverage

import org.jacoco.agent.rt.internal_43f5073.Agent
import org.jacoco.agent.rt.internal_43f5073.core.runtime.AgentOptions
import org.jacoco.core.analysis.Analyzer
import org.jacoco.core.analysis.CoverageBuilder
import org.jacoco.core.analysis.IClassCoverage
import org.jacoco.core.data.ExecutionDataReader
import org.jacoco.core.data.ExecutionDataStore
import java.io.File

class CoverageExtractor(private val originalByteCode: File?, packagePrefix: String) {
    private val agent = Agent.getInstance(AgentOptions("output=none,dumponexit=false,excludes=*,includes=$packagePrefix*"))
    private val original: ByteArray
    private lateinit var lastExecutionData: Collection<IClassCoverage>
    internal var getFresh: Boolean = true

    init {
        require(originalByteCode != null && originalByteCode.exists()) { "The path to the original bytecode is invalid! Given: $originalByteCode" }
        original = originalByteCode.readBytes()
        // reset any coverage from startup if there is any
        agent.reset()
    }

    internal fun getCoveredClasses(): Collection<IClassCoverage> {
        if (getFresh) {
            val coverageBuilder = CoverageBuilder()
            val executionData = getExecutionData()
            val analyzer = Analyzer(executionData, coverageBuilder)
            analyzer.analyzeAll(original.inputStream(), originalByteCode.toString())
            getFresh = false
            lastExecutionData = coverageBuilder.classes
        }
        return lastExecutionData
    }

    private fun getExecutionData(): ExecutionDataStore {
        val executionData = agent.getExecutionData(getFresh)
        val executionDataReader = ExecutionDataReader(executionData.inputStream())
        val execStore = ExecutionDataStore()
        executionDataReader.setExecutionDataVisitor(execStore)
        executionDataReader.setSessionInfoVisitor {} // must avoid NPE
        executionDataReader.read()
        return execStore
    }
}
