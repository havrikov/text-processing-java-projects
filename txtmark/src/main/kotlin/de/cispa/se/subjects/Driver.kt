package de.cispa.se.subjects

import com.github.rjeschke.txtmark.Processor

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.github.rjeschke.txtmark"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        Processor.process(text)
        Processor.process("[\$PROFILE\$]: extended\n $text")
    }
}
