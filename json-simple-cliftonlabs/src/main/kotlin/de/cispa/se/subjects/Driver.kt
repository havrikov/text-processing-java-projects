package de.cispa.se.subjects

import com.github.cliftonlabs.json_simple.Jsonable
import com.github.cliftonlabs.json_simple.Jsoner

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.github.cliftonlabs.json_simple"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        Jsoner.prettyPrint(text)

        val deserialized = Jsoner.deserialize(text)

        Jsoner.serialize(deserialized, NopWriter)
        Jsoner.serializeCarelessly(deserialized, NopWriter)
        Jsoner.serializeStrictly(deserialized, NopWriter)

        if (deserialized is Jsonable) deserialized.toJson(NopWriter)
    }
}
