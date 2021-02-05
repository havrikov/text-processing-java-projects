package de.cispa.se.subjects

import net.quux00.simplecsv.CsvParserBuilder
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "net.quux00.simplecsv"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val parser = CsvParserBuilder()
            .allowUnbalancedQuotes(true)
            .alwaysQuoteOutput(true)
            .multiLine(true)
            .retainEscapeChars(true)
            .retainOuterQuotes(true)
            .strictQuotes(true)
            .supportRfc4180QuotedQuotes(true)
            .trimWhitespace(true)
            .threadSafe(false)
            .build()

        with(file) {
            reader().use {
                val lines = net.quux00.simplecsv.CsvReaderBuilder(it).csvParser(parser).build().readAll()
                net.quux00.simplecsv.CsvWriterBuilder(NopWriter).build().writeAll(lines)
            }
            reader().use {
                val lines = net.quux00.simplecsv.CsvReaderBuilder(it).csvParser(net.quux00.simplecsv.SimpleCsvParser()).build().readAll()
                net.quux00.simplecsv.CsvWriterBuilder(NopWriter).build().writeAll(lines)
            }
        }
    }
}
