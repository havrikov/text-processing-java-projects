package de.cispa.se.subjects

import org.json.simple.JSONAware
import org.json.simple.JSONStreamAware
import org.json.simple.parser.ContentHandler
import org.json.simple.parser.JSONParser
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.json.simple"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        JSONParser().parse(file.bufferedReader(), object : ContentHandler {
            override fun startObjectEntry(key: String?) = true
            override fun startJSON() = Unit
            override fun endArray() = true
            override fun endObject() = true
            override fun endObjectEntry() = true
            override fun primitive(value: Any?) = true
            override fun startArray() = true
            override fun endJSON() = Unit
            override fun startObject() = true
        })

        val parsed = JSONParser().parse(file.bufferedReader())
        if (parsed is JSONAware) parsed.toJSONString()
        if (parsed is JSONStreamAware) parsed.writeJSONString(NopWriter)
    }
}
