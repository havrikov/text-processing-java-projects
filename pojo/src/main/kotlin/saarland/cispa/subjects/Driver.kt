package saarland.cispa.subjects

import com.sun.codemodel.CodeWriter
import com.sun.codemodel.JCodeModel
import com.sun.codemodel.JPackage
import org.jsonschema2pojo.DefaultGenerationConfig
import org.jsonschema2pojo.GenerationConfig
import org.jsonschema2pojo.Jackson2Annotator
import org.jsonschema2pojo.SchemaGenerator
import org.jsonschema2pojo.SchemaMapper
import org.jsonschema2pojo.SchemaStore
import org.jsonschema2pojo.SourceType
import org.jsonschema2pojo.rules.RuleFactory
import java.io.File
import java.io.OutputStream

object Driver : SubjectExecutor() {
    override val packagePrefix = "org.jsonschema2pojo"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processFile(file: File) {
        val codeModel = JCodeModel()
        val mapper = SchemaMapper(RuleFactory(config, Jackson2Annotator(config), SchemaStore()), SchemaGenerator())
        mapper.generate(codeModel, "ClassName", "com.example", file.toURI().toURL())
        codeModel.build(NopCodeWriter)
    }

    private val NopCodeWriter: CodeWriter = object : CodeWriter() {
        override fun openBinary(pkg: JPackage?, fileName: String?): OutputStream = NopOutputStream
        override fun close() = Unit
    }

    private val config: GenerationConfig = object : DefaultGenerationConfig() {
        override fun isGenerateBuilders(): Boolean = true
        override fun isUsePrimitives(): Boolean = true
        override fun isUseLongIntegers(): Boolean = true
        override fun isUseDoubleNumbers(): Boolean = true
        override fun isIncludeHashcodeAndEquals(): Boolean = true
        override fun isIncludeToString(): Boolean = true
        override fun getSourceType(): SourceType = SourceType.JSON
        override fun isRemoveOldOutput(): Boolean = true
        override fun isUseJodaDates(): Boolean = true
        override fun isUseJodaLocalTimes(): Boolean = true
        override fun isUseJodaLocalDates(): Boolean = true
        override fun isIncludeConstructors(): Boolean = true

    }
}
