package saarland.cispa.subjects

import com.eclipsesource.json.Json
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.eclipsesource.json"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        file.reader().use {
            val jsonValue = Json.parse(it)
            with(jsonValue) {
                toString()
                when {
                    isArray -> asArray()
                    isBoolean -> asBoolean()
                    isFalse -> asBoolean()
                    isNull -> null
                    isNumber -> asFloat()
                    isObject -> asObject()
                    isString -> asString()
                    isTrue -> asBoolean()
                    else -> Unit
                }
            }
        }
    }
}
