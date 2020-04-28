package saarland.cispa.subjects

import java.io.File

/** Subject Drivers should subclass this and call processArgs in their main */
abstract class SubjectExecutor {

    /** Subclasses should just call this method from their main */
    protected fun processArgs(args: Array<String>): Unit = LocalSubjectRunner(this).main(args)

    /** This specifies the classes to be instrumented by jacoco */
    abstract val packagePrefix: String

    /** Override this method in the driver to process a file handle */
    open fun processFile(file: File): Unit = processInput(file.readText())

    /** Override this method in the driver to process the file text */
    open fun processInput(text: String): Unit = throw NotImplementedError("You forgot to override processInput or processFile in ${this.javaClass.simpleName}!")
}
