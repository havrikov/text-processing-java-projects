package saarland.cispa.subjects

import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.commonmark"

    private val parser = Parser.builder().build()
    private val renderer = HtmlRenderer.builder().build()

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        renderer.render(parser.parse(text))
    }
}
