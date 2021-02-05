package de.cispa.se.subjects

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.fasterxml.jackson.dataformat"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val mapper = CsvMapper()
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY)
        mapper.enable(CsvParser.Feature.ALLOW_TRAILING_COMMA)
        mapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE)
        mapper.enable(CsvParser.Feature.INSERT_NULLS_FOR_MISSING_COLUMNS)
        mapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES)
        mapper.enable(CsvParser.Feature.TRIM_SPACES)
        mapper.enable(*JsonParser.Feature.values())
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, *DeserializationFeature.values().filter { !it.name.startsWith("FAIL") }.toTypedArray())

        val writer = mapper.writerFor(Array<String>::class.java)
        val iterator = mapper.readerFor(Array<String>::class.java).readValues<Array<String>>(file)
        for (row in iterator.readAll()) {
            writer.writeValue(NopWriter, row)
        }
    }
}
