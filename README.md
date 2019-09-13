# Text Processing Java Projects

This is a collection of drivers for projects that take structured text inputs.  
The projects are built with [jacoco](https://www.eclemma.org/jacoco/) instrumentation and report code coverage and thrown exceptions.

## Build Instructions
You require java `1.8` or greater.  
To build all projects simply execute `./gradlew build` (or `.\gradlew.bat build` on Windows) in the project root directory.  
This will generate instrumented, executable jars in the `build/libs` directory.

Additionally, the build process will download the original, uninstrumented versions of the projects' artifacts into `build/originals`.
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

## Repository Structure

This repository is organized as a gradle multi-project where each subdirectory encapsulates a driver for a project, with a few notable exceptions:

```
.
├── argo      <-- driver for project argo
├── autolink  <-- driver for project autolink
├── ...       <-- more project drivers...
├── build     <-- the output directory where the built projects end up
├── buildSrc  <-- single source of truth for dependency and project versions
├── gradle    <-- gradle wrapper, so you don't have to install a build tool
└── utils     <-- this contains the entry point, command line processing, and coverage and exception reporting; it is used in all drivers
```

## Projects
These are the projects, which are currently supported:

#### JSON
Project | Version | Instrumented Package
---     | --- | ---
[argo](http://argo.sourceforge.net/) | `5.4` | `argo`
[fastjson](https://github.com/alibaba/fastjson) | `1.2.51` | `com.alibaba.fastjson`
[genson](https://owlike.github.io/genson/) | `1.4` | `com.owlike.genson`
[gson](https://github.com/google/gson) | `2.8.5` | `com.google.gson`
[jackson-databind](https://github.com/FasterXML/jackson-databind) | `2.9.8` | `com.fasterxml.jackson`
[json-flattener](https://github.com/wnameless/json-flattener) | `0.6.0` | `com.github.wnameless.json`
[json-java](https://github.com/stleary/JSON-java/) | `20180813` | `org.json`
[json-simple](https://github.com/fangyidong/json-simple) | `1.1.1` | `org.json.simple`
[json-simple-cliftonlabs](https://github.com/cliftonlabs/json-simple) | `3.0.2` | `com.github.cliftonlabs.json_simple`
[minimal-json](https://github.com/ralfstx/minimal-json) | `0.9.5` | `com.eclipsesource.json`
[pojo](https://github.com/joelittlejohn/jsonschema2pojo) | `1.0.0` | `org.jsonschema2pojo`

#### CSV
Project | Version | Instrumented Package
---     | --- | ---
[commons-csv](https://commons.apache.org/proper/commons-csv/) | `1.6` | `org.apache.commons.csv`
[jackson-dataformat-csv](https://github.com/FasterXML/jackson-dataformats-text/tree/master/csv) | `2.9.8` | `com.fasterxml.jackson.dataformat`
[jcsv](https://code.google.com/archive/p/jcsv/) | `1.4.0` | `com.googlecode.jcsv`
[sfm-csv](https://github.com/arnaudroger/SimpleFlatMapper) | `6.1.1` | `org.simpleflatmapper.csv`
[simplecsv](https://github.com/quux00/simplecsv) | `2.1` | `net.quux00.simplecsv`
[super-csv](https://github.com/super-csv/super-csv) | `2.4.0` | `org.supercsv`

#### URL
Project | Version | Instrumented Package
---     | --- | ---
[autolink](https://github.com/robinst/autolink-java) | `0.9.0` | `org.nibor.autolink`
[galimatias](https://github.com/smola/galimatias) | `0.2.1` | `io.mola.galimatias`
[jurl](https://github.com/anthonynsimon/jurl) | `v0.3.0` | `com.anthonynsimon.url`
[url-detector](https://github.com/linkedin/URL-Detector) | `0.1.17` | `com.linkedin.urls.detection`

#### Markdown
Project | Version | Instrumented Package
---     | --- | ---
[commonmark](https://github.com/atlassian/commonmark-java) | `0.11.0` | `org.commonmark`
[markdown4j](https://github.com/jdcasey/markdown4j) | `2.2-cj-1.1` | `org.markdown4j`
[txtmark](https://github.com/rjeschke/txtmark) | `0.13` | `com.github.rjeschke.txtmark`
