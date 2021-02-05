package saarland.cispa.subjects

import org.w3c.flute.parser.Parser

object Driver : SACDriver() {
    override val packagePrefix = "org.w3c.flute"
    override val parser = Parser()

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)
}
