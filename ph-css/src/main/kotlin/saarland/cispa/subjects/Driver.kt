package saarland.cispa.subjects

import com.helger.css.ECSSVersion
import com.helger.css.reader.CSSReader
import java.io.File
import java.nio.charset.StandardCharsets

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.helger.css"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        CSSReader.readFromFile(file, StandardCharsets.UTF_8, ECSSVersion.CSS30)
    }
}
