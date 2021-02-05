package de.cispa.se.subjects

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.JsonNodeType
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.ValueNode

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.fasterxml.jackson"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val mapper = ObjectMapper()
        mapper.setDefaultPrettyPrinter(DefaultPrettyPrinter())
        val map = mapper.readValue(text, if (text.trimStart()[0] == '{') Map::class.java else List::class.java)
        mapper.writeValue(NopWriter, map)

        // Parser and Generator from core package
        val factory = JsonFactory()
        val parser = factory.createParser(text)
        val generator = factory.createGenerator(NopWriter)
        parseAndGenerate(parser, generator)
        generator.close()

        // Node Tree read in and write out with mapper
        val root = mapper.readTree(text)
        val generatorForTree = factory.createGenerator(NopWriter)
        mapper.writeTree(generatorForTree, root)
        generatorForTree.close()

        // Node Tree read in (databind), iterate and write with generator (core)
        val rootToIterate = mapper.readTree(text)
        val generatorForTreeIteration = factory.createGenerator(NopWriter)
        rootToIterate.handle(generatorForTreeIteration)
        generatorForTreeIteration.close()
    }

    private fun JsonNode.handle(generator: JsonGenerator) {
        when (this) {
            is ArrayNode -> this.handle(generator)
            is ObjectNode -> this.handle(generator)
            is ValueNode -> this.handle(generator)
        }
    }

    private fun ArrayNode.handle(generator: JsonGenerator) {
        generator.writeStartArray()
        this.elements().forEach { it.handle(generator) }
        generator.writeEndArray()
    }

    private fun ObjectNode.handle(generator: JsonGenerator) {
        generator.writeStartObject()
        this.fields().forEach {
            generator.writeFieldName(it.key)
            it.value.handle(generator)
        }
        generator.writeEndObject()
    }


    private fun ValueNode.handle(generator: JsonGenerator) {
        when (this.nodeType) {
            JsonNodeType.NULL -> generator.writeNull()
            JsonNodeType.BOOLEAN -> generator.writeBoolean(this.asBoolean())
            JsonNodeType.STRING -> generator.writeString(this.asText())
            JsonNodeType.NUMBER -> when {
                this.isIntegralNumber -> generator.writeNumber(this.asLong())
                this.isFloatingPointNumber -> generator.writeNumber(this.asDouble())
            }
            else -> {
            }
        }
    }

    private fun parseAndGenerate(parser: JsonParser, generator: JsonGenerator) {
        while (!parser.isClosed) {
            val token = parser.nextToken()
            when (token) {
                JsonToken.START_OBJECT -> generator.writeStartObject()
                JsonToken.END_OBJECT -> generator.writeEndObject()
                JsonToken.START_ARRAY -> generator.writeStartArray()
                JsonToken.END_ARRAY -> generator.writeEndArray()
                JsonToken.VALUE_FALSE -> generator.writeBoolean(parser.valueAsBoolean)
                JsonToken.VALUE_TRUE -> generator.writeBoolean(parser.valueAsBoolean)
                JsonToken.VALUE_NUMBER_INT -> generator.writeNumber(parser.valueAsInt)
                JsonToken.VALUE_NUMBER_FLOAT -> generator.writeNumber(parser.valueAsDouble)
                JsonToken.VALUE_STRING -> generator.writeString(parser.valueAsString)
                JsonToken.VALUE_NULL -> generator.writeNull()
                JsonToken.FIELD_NAME -> generator.writeFieldName(parser.currentName) // xxx check
                JsonToken.NOT_AVAILABLE -> Unit
                JsonToken.VALUE_EMBEDDED_OBJECT -> Unit
                null -> return // end of input as per spec
            }
        }
    }
}
