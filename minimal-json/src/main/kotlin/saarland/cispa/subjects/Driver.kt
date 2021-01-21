package saarland.cispa.subjects

import com.eclipsesource.json.Json
import com.eclipsesource.json.WriterConfig
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.eclipsesource.json"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val jsonValue = file.reader().use {
            Json.parse(it)
        }
        jsonValue.writeTo(NopWriter, WriterConfig.MINIMAL)
        jsonValue.writeTo(NopWriter, WriterConfig.PRETTY_PRINT)

        jsonValue.run {
            when {
                isArray -> asArray()
                isBoolean -> asBoolean()
                isFalse -> asBoolean()
                isNull -> Unit
                isNumber -> asFloat()
                isObject -> asObject()
                isString -> asString()
                isTrue -> asBoolean()
                else -> Unit
            }
        }
    }
}
