package saarland.cispa.subjects

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.StringReader

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.google.gson"

    private val gson = Gson()

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        // Parsing with JsonParser
        JsonParser.parseString(text).handle(JsonWriter(NopWriter))

        // iterate over json input stream with JsonReader and writing in an output stream via JsonWriter
        // package com.google.gson.stream
        JsonReader(StringReader(text)).use {
            handle(it, JsonWriter(NopWriter))
        }

        // (De)Serialization with Gson Class
        val start = text.trimStart()[0]
        when (start) {
            '{' -> {
                val map = gson.fromJson(text, Map::class.java)
                gson.toJson(map)
            }
            '[' -> {
                val array = gson.fromJson(text, Array<Any>::class.java)
                gson.toJson(array)
            }
        }
    }

    private fun JsonElement.handle(writer: JsonWriter) {
        when (this) {
            is JsonObject -> this.handle(writer)
            is JsonArray -> this.handle(writer)
            is JsonNull -> this.handle(writer)
            is JsonPrimitive -> this.handle(writer)
        }
    }

    private fun JsonObject.handle(writer: JsonWriter) {
        writer.beginObject()
        this.entrySet().forEach {
            writer.name(it.key)
            it.value.handle(writer)
        }
        writer.endObject()
    }

    private fun JsonArray.handle(writer: JsonWriter) {
        writer.beginArray()
        this.forEach { it.handle(writer) }
        writer.endArray()
    }

    private fun JsonNull.handle(writer: JsonWriter) {
        writer.nullValue()
    }

    private fun JsonPrimitive.handle(writer: JsonWriter) {
        when {
            this.isBoolean -> writer.value(this.asBoolean)
            this.isString -> writer.value(this.asString)
            this.isNumber -> writer.value(this.asNumber)
        }
    }

    private fun handle(reader: JsonReader, writer: JsonWriter) {
        while (reader.hasNext()) {
            val token: JsonToken = reader.peek()
            when (token) {
                JsonToken.BEGIN_ARRAY -> {
                    reader.beginArray()
                    writer.beginArray()
                }
                JsonToken.END_ARRAY -> {
                    reader.endArray()
                    writer.endArray()
                }
                JsonToken.BEGIN_OBJECT -> {
                    reader.beginObject()
                    writer.beginObject()
                }
                JsonToken.END_OBJECT -> {
                    reader.endObject()
                    writer.endObject()
                }
                JsonToken.NAME -> writer.name(reader.nextName())
                JsonToken.STRING -> writer.value(reader.nextString())
                JsonToken.NUMBER -> writer.value(reader.nextDouble())
                JsonToken.BOOLEAN -> writer.value(reader.nextBoolean())
                JsonToken.NULL -> {
                    reader.nextNull()
                    writer.nullValue()
                }
                JsonToken.END_DOCUMENT -> {
//                    for some reason flush throws IllegalStateException here
//                    writer.flush()
                    writer.close()
                    return
                }
            }
        }
    }
}
