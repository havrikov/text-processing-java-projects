package de.cispa.se.subjects

import java.io.Writer

object NopWriter : Writer() {
    override fun write(cbuf: CharArray?, off: Int, len: Int) {}
    override fun flush() {}
    override fun close() {}
}
