import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ReadmeGenerationTask : DefaultTask() {

    @TaskAction
    fun generateReadme() {
        File("${project.projectDir}/README.md").writeText(
            """# Text Processing Java Projects

This is a collection of drivers for projects that take structured text inputs.  
The projects are built with [jacoco](https://www.eclemma.org/jacoco/) instrumentation and report code coverage.

## Build Instructions
You require java `1.8` or greater.  
To build all projects simply execute `./gradlew build` (or `.\gradlew.bat build` on Windows) in the project root directory.  
This will generate instrumented, executable jars in the `build/libs` directory.

There is also the command `./gradlew downloadOriginalJar`,
which will download the original, uninstrumented versions of the projects' artefacts into `build/originals`.
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
[json-simple](https://github.com/fangyidong/json-simple) | `${Deps.jsonSimpleVersion}` | `org.json.simple`
[json-simple-cliftonlabs](https://github.com/cliftonlabs/json-simple) | `${Deps.jsonSimpleCliftonlabsVersion}` | `com.github.cliftonlabs.json_simple`
[minimal-json](https://github.com/ralfstx/minimal-json) | `${Deps.minimalJsonVersion}` | `com.eclipsesource.json`
[pojo](https://github.com/joelittlejohn/jsonschema2pojo) | `${Deps.pojoVersion}` | `org.jsonschema2pojo`

#### CSV
Project | Version | Instrumented Package
---     | --- | ---
[commons-csv](https://commons.apache.org/proper/commons-csv/) | `${Deps.commonsCSVVersion}` | `org.apache.commons.csv`
[jackson-dataformat-csv](https://github.com/FasterXML/jackson-dataformats-text/tree/master/csv) | `${Deps.jacksonDataformatCSVVersion}` | `com.fasterxml.jackson.dataformat`
[jcsv](https://code.google.com/archive/p/jcsv/) | `${Deps.jcsvVersion}` | `com.googlecode.jcsv`
[sfm-csv](https://github.com/arnaudroger/SimpleFlatMapper) | `${Deps.sfmCSVVersion}` | `org.simpleflatmapper.csv`
[simplecsv](https://github.com/quux00/simplecsv) | `${Deps.simpleCSVVersion}` | `net.quux00.simplecsv`
[super-csv](https://github.com/super-csv/super-csv) | `${Deps.superCSVVersion}` | `org.supercsv`

#### URL
Project | Version | Instrumented Package
---     | --- | ---
[autolink](https://github.com/robinst/autolink-java) | `${Deps.autolinkVersion}` | `org.nibor.autolink`
[galimatias](https://github.com/smola/galimatias) | `${Deps.galimatiasVersion}` | `io.mola.galimatias`
[jurl](https://github.com/anthonynsimon/jurl) | `${Deps.jurlVersion}` | `com.anthonynsimon.url`
[url-detector](https://github.com/linkedin/URL-Detector) | `${Deps.urlDetectorVersion}` | `com.linkedin.urls.detection`

#### Markdown
Project | Version | Instrumented Package
---     | --- | ---
[commonmark](https://github.com/atlassian/commonmark-java) | `${Deps.commonmarkVersion}` | `org.commonmark`
[markdown4j](https://github.com/jdcasey/markdown4j) | `${Deps.markdown4jVersion}` | `org.markdown4j`
[txtmark](https://github.com/rjeschke/txtmark) | `${Deps.txtmarkVersion}` | `com.github.rjeschke.txtmark`
"""
        )
    }
}
