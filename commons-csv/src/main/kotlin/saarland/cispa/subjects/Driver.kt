package saarland.cispa.subjects

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.charset.StandardCharsets

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.apache.commons.csv"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val formats = listOf(
            CSVFormat.DEFAULT,
            CSVFormat.EXCEL,
            CSVFormat.INFORMIX_UNLOAD,
            CSVFormat.INFORMIX_UNLOAD_CSV,
            CSVFormat.MYSQL,
            CSVFormat.POSTGRESQL_CSV,
            CSVFormat.POSTGRESQL_TEXT,
            CSVFormat.RFC4180
        )

        for (formatIn in formats) {
            for (formatOut in formats) {
                CSVParser.parse(file, StandardCharsets.UTF_8, formatIn).use { parser ->
                    CSVPrinter(NopWriter, formatOut).use {
                        it.printRecords(parser.records)
                    }
                }
            }
        }
    }
}
