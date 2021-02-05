package saarland.cispa.subjects

import org.markdownj.MarkdownProcessor

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.markdownj"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val processor = MarkdownProcessor()
        processor.markdown(text)
    }
}
