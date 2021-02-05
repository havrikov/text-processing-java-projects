package saarland.cispa.subjects

import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.w3c.css.css"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        org.w3c.css.css.CssValidator.main(arrayOf("-profile", "css3svg", file.toURI().toURL().toString()))
    }
}
