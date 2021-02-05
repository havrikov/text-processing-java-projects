package de.cispa.se.subjects

import com.googlecode.jcsv.reader.internal.CSVReaderBuilder
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.googlecode.jcsv"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val csvReader = CSVReaderBuilder.newDefaultReader(file.reader())
        val csvWriter = CSVWriterBuilder.newDefaultWriter(NopWriter)
        csvWriter.writeAll(csvReader.readAll())
    }
}
