package de.cispa.se.subjects

import delight.rhinosandox.RhinoSandboxes

object Driver : SubjectExecutor() {
    override val packagePrefix = "delight.rhinosandox"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val sandbox = RhinoSandboxes.create()
        sandbox.setMaxDuration(100)
        sandbox.eval(null, text)
    }
}
