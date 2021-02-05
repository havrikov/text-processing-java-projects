package de.cispa.se.subjects

import com.anthonynsimon.url.URL
import java.io.File

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.anthonynsimon.url"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val base: URL = URL.parse(file.readText())
        base.scheme
        base.username
        base.password
        base.host
        base.path
        base.fragment
        base.path
        base.query
        base.queryPairs
        base.toString()
        base.resolveReference("./../test.html")
    }
}
