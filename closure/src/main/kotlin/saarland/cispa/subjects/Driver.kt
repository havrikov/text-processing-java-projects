package saarland.cispa.subjects

import com.google.javascript.jscomp.ClosureDriverCore
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.google.javascript.jscomp"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) = ClosureDriverCore.execute(file)
}
