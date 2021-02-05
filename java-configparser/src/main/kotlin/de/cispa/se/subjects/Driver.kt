package de.cispa.se.subjects

import ca.szc.configparser.Ini
import java.io.BufferedWriter
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "ca.szc.configparser"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val reader = Ini()
        reader.isAllowDuplicates = true
        reader.isAllowInterpolation = true
        reader.isAllowNoValue = true
        reader.isEmptyLinesInValues = true
        reader.isSpaceAroundDelimiters = true

        val ini = reader.read(file.bufferedReader())
        ini.sections
        ini.write(BufferedWriter(NopWriter))
    }
}
