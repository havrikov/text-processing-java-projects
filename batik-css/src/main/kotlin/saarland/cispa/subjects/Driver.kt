package saarland.cispa.subjects

import org.apache.batik.css.parser.Parser

object Driver : SACDriver() {
    override val packagePrefix = "org.apache.batik.css"
    override val parser = Parser()

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)
}
