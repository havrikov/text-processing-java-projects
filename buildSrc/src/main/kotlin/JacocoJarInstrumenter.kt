import org.gradle.api.artifacts.transform.InputArtifact
import org.gradle.api.artifacts.transform.TransformAction
import org.gradle.api.artifacts.transform.TransformOutputs
import org.gradle.api.artifacts.transform.TransformParameters
import org.gradle.api.file.FileSystemLocation
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.jacoco.core.JaCoCo
import org.jacoco.core.instr.Instrumenter
import org.jacoco.core.runtime.OfflineInstrumentationAccessGenerator


abstract class JacocoJarInstrumenter : TransformAction<TransformParameters.None> {
    @get:InputArtifact
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    abstract val inputArtifact: Provider<FileSystemLocation>

    private val instrumenter = Instrumenter(OfflineInstrumentationAccessGenerator())

    override fun transform(outputs: TransformOutputs) {
        val src = inputArtifact.get().asFile
        val dest = outputs.file("${src.nameWithoutExtension}-instrumented.jar")
        println("Instrumenting ${src.name} with JaCoCo version ${JaCoCo.VERSION}")
        src.inputStream().use { input ->
            dest.outputStream().use { output ->
                instrumenter.instrumentAll(input, output, src.absolutePath)
            }
        }
    }
}
