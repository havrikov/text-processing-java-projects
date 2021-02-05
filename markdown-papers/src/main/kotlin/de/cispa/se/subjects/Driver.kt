package de.cispa.se.subjects

import org.tautua.markdownpapers.Markdown
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.tautua.markdownpapers"

    private val markdown = Markdown()

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) = file.reader().use {
        markdown.transform(it, NopWriter)
    }
}
