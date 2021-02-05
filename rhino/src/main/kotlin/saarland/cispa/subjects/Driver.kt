package saarland.cispa.subjects

import org.mozilla.javascript.Callable
import org.mozilla.javascript.Context
import org.mozilla.javascript.ContextFactory
import org.mozilla.javascript.Scriptable
import java.io.File
import java.util.concurrent.TimeoutException

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.mozilla.javascript"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        file.reader().use {
            val context = Context.enter()
            val scope = context.initStandardObjects()
            context.evaluateReader(scope, it, file.absolutePath, 1, null)
        }
    }

    init {
        ContextFactory.initGlobal(MyFactory())
    }

    private class MyFactory : ContextFactory() {
        // Custom Context to store execution time.

        inner class MyContext(var startTime: Long = 0) : Context(this)

        override fun makeContext(): Context {
            val ctx = MyContext()
            ctx.optimizationLevel = -1
            ctx.instructionObserverThreshold = 10000
            return ctx
        }

        override fun observeInstructionCount(cx: Context?, instructionCount: Int) {
            val mcx = cx as MyContext
            val currentTime = System.currentTimeMillis()
            if (currentTime - mcx.startTime > 100) {
                // More than 100 milliseconds from Context creation time:
                // it is time to stop the script.
                // Throw Error instance to ensure that script will never
                // get control back through catch or finally.
                throw TimeoutException("The script took too long to execute!")
            }
        }

        override fun doTopCall(callable: Callable?, cx: Context?, scope: Scriptable?, thisObj: Scriptable?, args: Array<out Any>?): Any {
            val mcx = cx as MyContext
            mcx.startTime = System.currentTimeMillis()
            return super.doTopCall(callable, cx, scope, thisObj, args)
        }
    }

}

