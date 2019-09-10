package saarland.cispa.subjects

import io.mola.galimatias.StrictErrorHandler
import io.mola.galimatias.URL
import io.mola.galimatias.URLParsingSettings

object Driver : SubjectExecutor() {
    override val packagePrefix = "io.mola.galimatias"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val url: URL = URL.parse(text)
        url.toJavaURI()
        url.toJavaURL()
        URL.parse(URLParsingSettings.create().withErrorHandler(StrictErrorHandler.getInstance()), text)
    }
}
