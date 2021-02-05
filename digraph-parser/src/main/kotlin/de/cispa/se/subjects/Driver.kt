package de.cispa.se.subjects

import com.paypal.digraph.parser.GraphParser
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.paypal.digraph.parser"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val parser = GraphParser(file.inputStream())
        parser.nodes.values.forEach { node ->
            node.toString()
            node.id
            node.attributes.values.forEach {
                it.toString()
            }
        }

        parser.edges.values.forEach { edge ->
            edge.toString()
            edge.node1
            edge.node2
        }
    }
}
