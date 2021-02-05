package saarland.cispa.subjects

import cz.vutbr.web.css.CSSFactory
import java.io.File
import java.nio.charset.StandardCharsets

object Driver : SubjectExecutor() {
    override val packagePrefix = "net.sf.cssbox"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        CSSFactory.parse(file.path, StandardCharsets.UTF_8.name())
    }
}
