package saarland.cispa.subjects.coverage

import org.jacoco.core.analysis.IClassCoverage
import org.jacoco.core.analysis.ICoverageNode

abstract class CoverageCounter(private val counterType: ICoverageNode.CounterEntity) {

    fun compute(classes: Collection<IClassCoverage>): Double {
        var hit = 0.0
        var all = 0.0
        for (clazz in classes) {
            val counter = clazz.getCounter(counterType)
            hit += counter.coveredCount
            all += counter.totalCount
        }
        return hit / all
    }
}

object ClassCoverageCounter : CoverageCounter(ICoverageNode.CounterEntity.CLASS)
object MethodCoverageCounter : CoverageCounter(ICoverageNode.CounterEntity.METHOD)
object LineCoverageCounter : CoverageCounter(ICoverageNode.CounterEntity.LINE)
object InstructionCoverageCounter : CoverageCounter(ICoverageNode.CounterEntity.INSTRUCTION)
object BranchCoverageCounter : CoverageCounter(ICoverageNode.CounterEntity.BRANCH)
