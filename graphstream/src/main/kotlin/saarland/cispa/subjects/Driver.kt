package saarland.cispa.subjects

import org.graphstream.graph.implementations.DefaultGraph
import org.graphstream.stream.file.FileSourceFactory
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.graphstream"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val filepath = file.absolutePath
        val g = DefaultGraph("g")
        val fs = FileSourceFactory.sourceFor(filepath)
        fs.addSink(g)
        fs.readAll(filepath)
    }
}
