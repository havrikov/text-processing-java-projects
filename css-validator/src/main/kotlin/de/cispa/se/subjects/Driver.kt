package de.cispa.se.subjects

import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.w3c.css.css"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        // FIXME this artifact seems to lack the main class contained in the binary distribution
        //  https://jigsaw.w3.org/css-validator/DOWNLOAD.html
        org.w3c.css.css.CssValidator.main(arrayOf("-profile", "css3svg", file.toURI().toURL().toString()))
    }
}
