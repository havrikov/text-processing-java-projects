package de.cispa.se.subjects

import com.github.onlynight.fastini.IniDocument
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.github.onlynight.fastini"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val document = IniDocument().fromPath(file.absolutePath)
        document.lines
        document.srcLines
        document["hello"]
    }
}
