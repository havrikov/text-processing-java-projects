package saarland.cispa.subjects

import com.steadystate.css.parser.CSSOMParser
import java.io.File
import org.w3c.css.sac.InputSource

object Driver : SubjectExecutor() {
    override val packagePrefix = "net.sourceforge.cssparser"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val parser = CSSOMParser()
        parser.parseStyleSheet(InputSource(file.reader()), null, null)
    }
}
