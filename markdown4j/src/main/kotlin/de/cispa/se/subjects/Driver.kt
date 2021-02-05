package de.cispa.se.subjects

import org.markdown4j.Markdown4jProcessor

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.markdown4j"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val processor = Markdown4jProcessor()
        processor.process(text)
    }
}
