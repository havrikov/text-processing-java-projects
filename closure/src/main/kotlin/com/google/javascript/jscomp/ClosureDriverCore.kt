package com.google.javascript.jscomp

import java.io.File

object ClosureDriverCore {

    fun execute(file: File) {
        val runner = CommandLineRunner(
            arrayOf(
                "--compilation_level", "SIMPLE",
                "--warning_level", "QUIET",
                "--new_type_inf", "true",
                "--strict_mode_input", "false",
                "--assume_function_wrapper", "true",
                "--formatting", "PRETTY_PRINT",
                "--isolation_mode", "IIFE",
                "--env", "BROWSER",
                "--module_resolution", "BROWSER",
                "--js", file.absolutePath
            )
        )

        runner.doRun()
    }
}
