package de.cispa.se.subjects

import com.linkedin.urls.detection.UrlDetector
import com.linkedin.urls.detection.UrlDetectorOptions
import java.util.EnumSet

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.linkedin.urls.detection"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    private val options = EnumSet.allOf(UrlDetectorOptions::class.java)

    override fun processInput(text: String) {
        options.forEach {
            val parser = UrlDetector(text, it)
            parser.backtracked

            for (url in parser.detect()) {
                url.scheme
                url.host
                url.path
                url.normalize()
            }
        }
    }
}
