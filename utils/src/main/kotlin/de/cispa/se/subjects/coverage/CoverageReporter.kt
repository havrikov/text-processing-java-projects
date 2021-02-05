package de.cispa.se.subjects.coverage

import com.opencsv.CSVWriter
import java.io.File

class CoverageReporter(targetFile: File, private val extractor: CoverageExtractor) {

    private val coverages = listOf(ClassCoverageCounter, MethodCoverageCounter, LineCoverageCounter, InstructionCoverageCounter, BranchCoverageCounter)
    private var fileCount = 0
    private val writer: CSVWriter

    init {
        // ensure the csv file can be written by creating its parent directory
        targetFile.absoluteFile.parentFile.mkdirs()
        writer = CSVWriter(targetFile.bufferedWriter())
        writer.writeNext(arrayOf("filenum", "filename", "class", "method", "line", "instruction", "branch"), false)
    }

    fun recordingCoverage(fileProcessor: (File) -> Unit): (File) -> Unit = { file ->
        try {
            fileProcessor(file)
        } finally {
            val classes = extractor.getCoveredClasses()
            val list = listOf(fileCount.toString(), file.name) + coverages.map { it.compute(classes).toString() }.toMutableList()
            writer.writeNext(list.toTypedArray(), false)
            fileCount += 1
        }
    }

    fun close() = writer.close()
}
