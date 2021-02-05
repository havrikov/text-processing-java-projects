package saarland.cispa.subjects

import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "guru.nidi"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val viz = Graphviz.fromFile(file)
        viz.render(Format.PNG)//.toImage()
    }
}
