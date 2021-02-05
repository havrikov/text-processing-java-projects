package de.cispa.se.subjects

import org.ini4j.Ini
import org.ini4j.IniPreferences
import org.ini4j.Options
import org.ini4j.Reg
import org.ini4j.Wini
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.ini4j"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        Wini(file).get("hello", "there")
        Ini(file).get("hello", "there")
        Reg(file).get("hello", "there")
        Options(file).get("hello", "there")
        IniPreferences(file.inputStream()).get("hello", "there")
    }
}
