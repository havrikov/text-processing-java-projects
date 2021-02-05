package de.cispa.se.subjects

import com.owlike.genson.Genson
import com.owlike.genson.stream.ObjectReader
import com.owlike.genson.stream.ObjectWriter
import com.owlike.genson.stream.ValueType
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.owlike.genson"

    private val genson = Genson()

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        file.reader().use {
            val obj = genson.deserialize(it, Object::class.java)
            genson.serialize(obj)
        }

        // Parsing with stream API
        // package : com.owlike.genson.stream
        file.reader().use {
            genson.createReader(it).use {
                val jsonWriter = genson.createWriter(NopWriter)
                handle(it, jsonWriter)
                jsonWriter.flush()
                jsonWriter.close()
            }
        }
    }

    private fun handle(reader: ObjectReader, writer: ObjectWriter) {
        while (reader.hasNext()) {
            val next: ValueType = reader.next()
            when (next) {
                ValueType.ARRAY -> {
                    reader.beginArray()
                    writer.beginArray()
                    handle(reader, writer)
                    reader.endArray()
                    writer.endArray()
                }
                ValueType.OBJECT -> {
                    reader.beginObject()
                    writer.beginObject()
                    writer.writeName(reader.name())
                    handle(reader, writer)
                    reader.endObject()
                    writer.endObject()
                }
                ValueType.BOOLEAN -> writer.writeBoolean(reader.valueAsBoolean())
                ValueType.INTEGER -> writer.writeNumber(reader.valueAsInt())
                ValueType.DOUBLE -> writer.writeNumber(reader.valueAsDouble())
                ValueType.STRING -> writer.writeString(reader.valueAsString())
                ValueType.NULL -> writer.writeNull()
            }
        }
    }

}
