package saarland.cispa.subjects

import delight.nashornsandbox.NashornSandboxes
import java.util.concurrent.Executors


object Driver : SubjectExecutor() {
    override val packagePrefix = "delight.nashornsandbox"
    private val executor = Executors.newSingleThreadExecutor()

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val sandbox = NashornSandboxes.create()
        sandbox.executor = executor
        sandbox.setMaxCPUTime(100)
        sandbox.eval(text)
    }
}
