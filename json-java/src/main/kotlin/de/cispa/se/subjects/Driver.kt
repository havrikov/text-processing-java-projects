package de.cispa.se.subjects

import org.json.CDL
import org.json.HTTP
import org.json.JSONArray
import org.json.JSONML
import org.json.JSONObject
import org.json.JSONTokener
import org.json.XML

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.json"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        JSONTokener(text).nextValue()
        when (text.trimStart()[0]) {
            '{' -> {
                val obj = JSONObject(text)
                obj.toString(2)
                XML.toString(obj)
                JSONML.toString(obj)
                JSONML.toJSONObject(text)
                HTTP.toJSONObject(HTTP.toString(obj))
            }
            '[' -> {
                val arr = JSONArray(text)
                arr.toString(2)
                XML.toString(arr)
                CDL.toString(arr)
            }
            else -> {
            }
        }
    }
}
