package saarland.cispa.subjects.coverage

import com.opencsv.CSVWriter
import java.io.File

class MethodReporter(targetFile: File, private val originalByteCode: File?, packagePrefix: String) {

    private val writer: CSVWriter
    private val extractor = CoverageExtractor(originalByteCode, packagePrefix)

    init {
        // ensure the csv file can be written by creating its parent directory
        targetFile.absoluteFile.parentFile.mkdirs()
        writer = CSVWriter(targetFile.bufferedWriter())
        writer.writeNext(arrayOf("input_file", "class_name", "method_name", "line", "instructions_missed", "instructions_hit"), false)
    }

    fun recordingCoverage(fileProcessor: (File) -> Unit): (File) -> Unit = { file ->
        try {
            fileProcessor(file)
        } finally {
            val classes = extractor.getCoveredClasses()
            classes.forEach {
                it.methods.forEach { method ->
                    val csvRow = mutableListOf(file.name, it.name)
                    csvRow.add(method.name)
                    csvRow.add(method.firstLine.toString())
                    csvRow.add(method.instructionCounter.missedCount.toString())
                    csvRow.add(method.instructionCounter.coveredCount.toString())
                    writer.writeNext(csvRow.toTypedArray(), false)
                }
            }
        }
    }

    fun close() = writer.close()
}
