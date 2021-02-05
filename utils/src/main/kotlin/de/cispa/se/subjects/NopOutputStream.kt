package de.cispa.se.subjects

import java.io.OutputStream

object NopOutputStream : OutputStream() {
    override fun write(b: Int) {}
}
