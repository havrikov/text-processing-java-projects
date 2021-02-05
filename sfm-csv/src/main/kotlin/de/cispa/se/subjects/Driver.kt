package de.cispa.se.subjects

import org.simpleflatmapper.csv.CsvParser
import org.simpleflatmapper.csv.CsvWriter
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.simpleflatmapper.csv"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) = file.bufferedReader().use {
        val iterator = CsvParser.mapTo(Array<String>::class.java).iterator(it)
        val writer = CsvWriter.from(Array<String>::class.java).to(NopWriter)
        while (iterator.hasNext()) {
            writer.append(iterator.next())
        }
    }
}
