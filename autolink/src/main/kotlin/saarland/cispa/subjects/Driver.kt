package saarland.cispa.subjects

import org.nibor.autolink.Autolink
import org.nibor.autolink.LinkExtractor
import org.nibor.autolink.LinkSpan
import org.nibor.autolink.LinkType
import java.util.EnumSet


object Driver : SubjectExecutor() {
    override val packagePrefix = "org.nibor.autolink"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.allOf(LinkType::class.java))
            .build()
        val links = linkExtractor.extractLinks(text)
        links.forEach {
            it.type
            it.beginIndex
            it.endIndex
        }

        Autolink.renderLinks(text, links) { _, _, _ -> }

        val spans = linkExtractor.extractSpans(text)

        buildString {
            for (span in spans) {
                val sub = text.substring(span.beginIndex, span.endIndex)
                if (span is LinkSpan) {
                    // span is a URL
                    append("<a href=\"")
                    append(sub)
                    append("\">")
                    append(sub)
                    append("</a>")
                } else {
                    // span is plain text before/after link
                    append(sub)
                }
            }
        }
    }
}
