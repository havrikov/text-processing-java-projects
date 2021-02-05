package de.cispa.se.subjects

import argo.format.CompactJsonFormatter
import argo.format.PrettyJsonFormatter
import argo.jdom.JdomParser
import argo.staj.StajParser

object Driver : SubjectExecutor() {
    override val packagePrefix = "argo"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        /// Info: Argo has the JdomParser, the SajParser and StajParser
        // SajParser seems to be internally used in JdomParser, therefore explicit calls are probably not needed

        // parse string to JsonNode
        val jdomParser = JdomParser()
        val node = jdomParser.parse(text)

        // convert back to string
        val prettyFormat = PrettyJsonFormatter()
        val compactFormat = CompactJsonFormatter()

        prettyFormat.format(node)
        compactFormat.format(node)

        // parse string with StajParser
        val stajParser = StajParser(text)
        while (stajParser.hasNext()) {
            val next = stajParser.next()
            next.jsonStreamElementType()
            if (next.hasText()) {
                next.text()
            }
        }
    }
}
