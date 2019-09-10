package saarland.cispa.subjects

import com.github.wnameless.json.flattener.FlattenMode
import com.github.wnameless.json.flattener.JsonFlattener
import com.github.wnameless.json.flattener.PrintMode
import com.github.wnameless.json.flattener.StringEscapePolicy
import com.github.wnameless.json.unflattener.JsonUnflattener
import java.util.EnumSet

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.github.wnameless.json"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    private val flattenModes = EnumSet.allOf(FlattenMode::class.java)
    private val printModes = EnumSet.allOf(PrintMode::class.java)
    private val escapePolicies = EnumSet.allOf(StringEscapePolicy::class.java)

    override fun processInput(text: String) {

        flattenModes.forEach { flattenMode ->
            printModes.forEach { printMode ->
                escapePolicies.forEach { escapePolicy ->
                    val json = JsonFlattener(text)
                        .withFlattenMode(flattenMode)
                        .withPrintMode(printMode)
                        .withStringEscapePolicy(escapePolicy)
                        .flatten()

                    flattenModes.forEach { flattenMode ->
                        printModes.forEach { printMode ->
                            JsonUnflattener(json)
                                .withFlattenMode(flattenMode)
                                .withPrintMode(printMode)
                                .unflatten()
                        }
                    }

                }
            }
        }
    }
}
