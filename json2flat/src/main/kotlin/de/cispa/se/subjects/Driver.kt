package de.cispa.se.subjects

import com.github.opendevl.JFlat

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.github.opendevl"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val flatMe = JFlat(text)
        val sheet = flatMe.json2Sheet()
        sheet.jsonAsSheet
        sheet.uniqueFields
    }
}
