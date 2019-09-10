package saarland.cispa.subjects

import org.supercsv.io.CsvListReader
import org.supercsv.io.CsvListWriter
import org.supercsv.io.CsvMapReader
import org.supercsv.io.CsvMapWriter
import org.supercsv.prefs.CsvPreference
import java.io.File
import java.io.FileReader

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.supercsv"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val prefs = listOf(
            CsvPreference.STANDARD_PREFERENCE,
            CsvPreference.EXCEL_PREFERENCE,
            CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE,
            CsvPreference.TAB_PREFERENCE
        )

        val path = file.absolutePath
        for (readPref in prefs) {
            for (writeProf in prefs) {
                readAsList(path, readPref, writeProf)
                readMap(path, readPref, writeProf)
            }
        }
    }

    private fun readAsList(path: String, readPreference: CsvPreference, writePreference: CsvPreference) {
        CsvListReader(FileReader(path), readPreference).use { listReader ->
            CsvListWriter(NopWriter, writePreference).use { listWriter ->
                listWriter.write(listReader.read())
            }
        }
    }

    private fun readMap(path: String, readPreference: CsvPreference, writePreference: CsvPreference) {
        CsvMapReader(FileReader(path), readPreference).use { mapReader ->
            CsvMapWriter(NopWriter, writePreference).use { mapWriter ->
                val header = mapReader.getHeader(true)
                mapWriter.write(mapReader.read(*header), *header)
            }
        }
    }
}
