import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ReadmeGenerationTask : DefaultTask() {

    @TaskAction
    fun generateReadme() {
        File("${project.projectDir}/README.md").writeText(
            """# Text Processing Java Projects

This is a collection of drivers for projects that take structured text inputs.  
The projects are built with [jacoco](https://www.eclemma.org/jacoco/) instrumentation and report code coverage and thrown exceptions.

## Build Instructions
You require java `1.8` or greater.  
To build all projects simply execute `./gradlew build` (or `.\gradlew.bat build` on Windows) in the project root directory.  
This will generate instrumented, executable jars in the `build/libs` directory.

There is also the command `./gradlew gatherOriginals`,
which will download the original, uninstrumented versions of the projects' artifacts into `build/originals`.
These are required for producing coverage reports.

## Running the Projects
After being built, every project can be invoked like any normal runnable jar.  
To get more information, you can call a project with the `--help` argument, e.g.:

```bash
java -jar build/libs/argo-subject.jar --help
```

For a more interesting example, if you have some inputs located in `~/tmp/json`, you can run the Argo json parser with the following command:

```bash
java -jar build/libs/argo-subject.jar \
--ignore-exceptions \
--log-exceptions argo.exceptions.json \
--report-coverage argo.coverage.csv \
--original-bytecode build/libs/originals/argo-5.4.jar \
~/tmp/json
```

This will execute the parser on all inputs in `~/tmp/json` and log all exceptions into `argo.exceptions.json` and produce a coverage report in `argo.coverage.csv`.

## Projects
These are the projects, which are currently supported:

#### JSON
Project | Version | Instrumented Package
---     | --- | ---
[argo](http://argo.sourceforge.net/) | `${Deps.argoVersion}` | `argo`
[fastjson](https://github.com/alibaba/fastjson) | `${Deps.fastjsonVersion}` | `com.alibaba.fastjson`
[genson](https://owlike.github.io/genson/) | `${Deps.gensonVersion}` | `com.owlike.genson`
[gson](https://github.com/google/gson) | `${Deps.gsonVersion}` | `com.google.gson`
[jackson-databind](https://github.com/FasterXML/jackson-databind) | `${Deps.jacksonDatabindVersion}` | `com.fasterxml.jackson`
[json-flattener](https://github.com/wnameless/json-flattener) | `${Deps.jsonFlattenerVersion}` | `com.github.wnameless.json`
[json-java](https://github.com/stleary/JSON-java/) | `${Deps.jsonJavaVersion}` | `org.json`
[json-simple-cliftonlabs](https://github.com/cliftonlabs/json-simple) | `${Deps.jsonSimpleCliftonlabsVersion}` | `com.github.cliftonlabs.json_simple`
[json-simple](https://github.com/fangyidong/json-simple) | `${Deps.jsonSimpleVersion}` | `org.json.simple`
[json2flat](https://github.com/opendevl/Json2Flat) | `${Deps.json2flatVersion}` | `com.github.opendevl`
[minimal-json](https://github.com/ralfstx/minimal-json) | `${Deps.minimalJsonVersion}` | `com.eclipsesource.json`
[pojo](https://github.com/joelittlejohn/jsonschema2pojo) | `${Deps.pojoVersion}` | `org.jsonschema2pojo`

#### URL
Project | Version | Instrumented Package
---     | --- | ---
[autolink](https://github.com/robinst/autolink-java) | `${Deps.autolinkVersion}` | `org.nibor.autolink`
[galimatias-nu](https://github.com/validator/galimatias) | `${Deps.galimatiasNuVersion}` | `io.mola.galimatias`
[galimatias](https://github.com/smola/galimatias) | `${Deps.galimatiasVersion}` | `io.mola.galimatias`
[jurl](https://github.com/anthonynsimon/jurl) | `${Deps.jurlVersion}` | `com.anthonynsimon.url`
[url-detector](https://github.com/linkedin/URL-Detector) | `${Deps.urlDetectorVersion}` | `com.linkedin.urls.detection`

#### Markdown
Project | Version | Instrumented Package
---     | --- | ---
[commonmark](https://github.com/atlassian/commonmark-java) | `${Deps.commonmarkVersion}` | `org.commonmark`
[flexmark](https://github.com/vsch/flexmark-java) | `${Deps.flexmarkVersion}` | `com.vladsch.flexmark`
[markdown-papers](http://github.com/lruiz/MarkdownPapers) | `${Deps.markdownPapersVersion}` | `org.tautua.markdownpapers`
[markdown4j](https://github.com/jdcasey/markdown4j) | `${Deps.markdown4jVersion}` | `org.markdown4j`
[markdownj](https://github.com/myabc/markdownj) | `${Deps.markdownjVersion}` | `org.markdownj`
[txtmark](https://github.com/rjeschke/txtmark) | `${Deps.txtmarkVersion}` | `com.github.rjeschke.txtmark`

#### CSV
Project | Version | Instrumented Package
---     | --- | ---
[commons-csv](https://commons.apache.org/proper/commons-csv/) | `${Deps.commonsCSVVersion}` | `org.apache.commons.csv`
[jackson-dataformat-csv](https://github.com/FasterXML/jackson-dataformats-text/tree/master/csv) | `${Deps.jacksonDataformatCSVVersion}` | `com.fasterxml.jackson.dataformat`
[jcsv](https://code.google.com/archive/p/jcsv/) | `${Deps.jcsvVersion}` | `com.googlecode.jcsv`
[sfm-csv](https://github.com/arnaudroger/SimpleFlatMapper) | `${Deps.sfmCSVVersion}` | `org.simpleflatmapper.csv`
[simplecsv](https://github.com/quux00/simplecsv) | `${Deps.simpleCSVVersion}` | `net.quux00.simplecsv`
[super-csv](https://github.com/super-csv/super-csv) | `${Deps.superCSVVersion}` | `org.supercsv`

#### JavaScript
Project | Version | Instrumented Package | Notes
---     | --- | --- | ---
[closure](https://github.com/google/closure-compiler) | `${Deps.closureVersion}` | `com.google.javascript.jscomp`
[nashorn-sandbox](https://github.com/javadelight/delight-nashorn-sandbox/) | `${Deps.nashornSandboxVersion}` | `delight.nashornsandbox` | Delegates to Nashorn (Rhino's precursor)
[rhino-sandbox](https://github.com/javadelight/delight-rhino-sandbox/) | `${Deps.rhinoSandboxVersion}` | `delight.rhinosandox` | Not a typo. Delegates to Rhino.
[rhino](https://github.com/mozilla/rhino/) | `${Deps.rhinoVersion}` | `org.mozilla.javascript`

#### CSS
Project | Version | Instrumented Package | Notes
---     | --- | --- | ---
[batik-css](https://xmlgraphics.apache.org/batik/javadoc/org/apache/batik/css/parser/package-summary.html) |  `${Deps.batikCssVersion}` | `org.apache.batik.css`
[css-validator](https://github.com/w3c/css-validator) | `${Deps.cssValidatorVersion}` | `org.w3c.css.css` | :warning: Currently unsupported because of Jacoco Error: "Method too large: org/w3c/css/parser/analyzer/CssParserTokenManager.jjMoveNfa_0 (II)I"
[cssparser](http://cssparser.sourceforge.net/) |  `${Deps.cssparserVersion}` | `net.sourceforge.cssparser` | :warning: Currently unsupported because of Jacoco Error: "Method too large: com/steadystate/css/parser/SACParserCSS21TokenManager.jjMoveNfa_0 (II)I"
[flute](https://www.w3.org/Style/CSS/SAC/) |  `${Deps.fluteVersion}` | `org.w3c.flute`
[jstyleparser](https://github.com/radkovo/jStyleParser/) |  `${Deps.jstyleparserVersion}` | `net.sf.cssbox`
[ph-css](https://github.com/phax/ph-css) | `${Deps.phCssVersion}` |  `com.helger.css` | :warning: Currently unsupported because of Jacoco Error: "Method too large: com/helger/css/parser/ParserCSS30TokenManager.jjMoveNfa_0 (II)I"

#### INI
Project | Version | Instrumented Package
---     | --- | ---
[fastini](https://github.com/onlynight/FastIni) | `${Deps.fastiniVersion}` | `com.github.onlynight.fastini`
[ini4j](http://ini4j.sourceforge.net/) | `${Deps.ini4jVersion}` | `org.ini4j`
[java-configparser](https://github.com/ASzc/java-configparser) | `${Deps.javaConfigparserVersion}` | `ca.szc.configparser`

#### Dot
Project | Version | Instrumented Package
---     | --- | ---
[digraph-parser](https://github.com/paypal/digraph-parser/) | `${Deps.digraphParserVersion}` | `com.paypal.digraph.parser`
[graphstream](http://graphstream-project.org/) | `${Deps.graphstreamVersion}` | `org.graphstream`
[graphviz-java](https://github.com/nidi3/graphviz-java/) | `${Deps.graphvizJavaVersion}` | `guru.nidi`
"""
        )
    }
}
