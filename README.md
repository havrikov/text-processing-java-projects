# Text Processing Java Projects

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

### Turning off the Instrumentation
You can set the `de.cispa.se.subjects.instrument` property to `false` to build the subjects without instrumenting them with jacoco.
They will still be runnable, but the reported coverage will be zero.

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
[argo](http://argo.sourceforge.net/) | `5.16` | `argo`
[fastjson](https://github.com/alibaba/fastjson) | `1.2.75` | `com.alibaba.fastjson`
[genson](https://owlike.github.io/genson/) | `1.6` | `com.owlike.genson`
[gson](https://github.com/google/gson) | `2.8.6` | `com.google.gson`
[jackson-databind](https://github.com/FasterXML/jackson-databind) | `2.12.0` | `com.fasterxml.jackson`
[json-flattener](https://github.com/wnameless/json-flattener) | `0.12.0` | `com.github.wnameless.json`
[json-java](https://github.com/stleary/JSON-java/) | `20201115` | `org.json`
[json-simple-cliftonlabs](https://github.com/cliftonlabs/json-simple) | `3.1.1` | `com.github.cliftonlabs.json_simple`
[json-simple](https://github.com/fangyidong/json-simple) | `1.1.1` | `org.json.simple`
[json2flat](https://github.com/opendevl/Json2Flat) | `1.0.3` | `com.github.opendevl`
[minimal-json](https://github.com/ralfstx/minimal-json) | `0.9.5` | `com.eclipsesource.json`
[pojo](https://github.com/joelittlejohn/jsonschema2pojo) | `1.0.2` | `org.jsonschema2pojo`

#### URL
Project | Version | Instrumented Package
---     | --- | ---
[autolink](https://github.com/robinst/autolink-java) | `0.10.0` | `org.nibor.autolink`
[galimatias-nu](https://github.com/validator/galimatias) | `0.1.3` | `io.mola.galimatias`
[galimatias](https://github.com/smola/galimatias) | `0.2.1` | `io.mola.galimatias`
[jurl](https://github.com/anthonynsimon/jurl) | `v0.4.2` | `com.anthonynsimon.url`
[url-detector](https://github.com/linkedin/URL-Detector) | `0.1.17` | `com.linkedin.urls.detection`

#### Markdown
Project | Version | Instrumented Package
---     | --- | ---
[commonmark](https://github.com/atlassian/commonmark-java) | `0.16.1` | `org.commonmark`
[flexmark](https://github.com/vsch/flexmark-java) | `0.34.48` | `com.vladsch.flexmark`
[markdown-papers](http://github.com/lruiz/MarkdownPapers) | `1.4.4` | `org.tautua.markdownpapers`
[markdown4j](https://github.com/jdcasey/markdown4j) | `2.2-cj-1.1` | `org.markdown4j`
[markdownj](https://github.com/myabc/markdownj) | `0.4` | `org.markdownj`
[txtmark](https://github.com/rjeschke/txtmark) | `0.13` | `com.github.rjeschke.txtmark`

#### CSV
Project | Version | Instrumented Package
---     | --- | ---
[commons-csv](https://commons.apache.org/proper/commons-csv/) | `1.8` | `org.apache.commons.csv`
[jackson-dataformat-csv](https://github.com/FasterXML/jackson-dataformats-text/tree/master/csv) | `2.11.3` | `com.fasterxml.jackson.dataformat`
[jcsv](https://code.google.com/archive/p/jcsv/) | `1.4.0` | `com.googlecode.jcsv`
[sfm-csv](https://github.com/arnaudroger/SimpleFlatMapper) | `8.2.3` | `org.simpleflatmapper.csv`
[simplecsv](https://github.com/quux00/simplecsv) | `2.1` | `net.quux00.simplecsv`
[super-csv](https://github.com/super-csv/super-csv) | `2.4.0` | `org.supercsv`

#### JavaScript
Project | Version | Instrumented Package | Notes
---     | --- | --- | ---
[closure](https://github.com/google/closure-compiler) | `v20201207` | `com.google.javascript.jscomp`
[nashorn-sandbox](https://github.com/javadelight/delight-nashorn-sandbox/) | `0.1.28` | `delight.nashornsandbox` | Delegates to Nashorn (Rhino's precursor)
[rhino-sandbox](https://github.com/javadelight/delight-rhino-sandbox/) | `0.0.11` | `delight.rhinosandox` | Not a typo. Delegates to Rhino.
[rhino](https://github.com/mozilla/rhino/) | `1.7.13` | `org.mozilla.javascript`

#### CSS
Project | Version | Instrumented Package | Notes
---     | --- | --- | ---
[batik-css](https://xmlgraphics.apache.org/batik/javadoc/org/apache/batik/css/parser/package-summary.html) |  `1.13` | `org.apache.batik.css`
[css-validator](https://github.com/w3c/css-validator) | `1.0.8` | `org.w3c.css.css` | :warning: Currently unsupported because of Jacoco Error: "Method too large: org/w3c/css/parser/analyzer/CssParserTokenManager.jjMoveNfa_0 (II)I"
[cssparser](http://cssparser.sourceforge.net/) |  `0.9.27` | `net.sourceforge.cssparser` | :warning: Currently unsupported because of Jacoco Error: "Method too large: com/steadystate/css/parser/SACParserCSS21TokenManager.jjMoveNfa_0 (II)I"
[flute](https://www.w3.org/Style/CSS/SAC/) |  `1.3` | `org.w3c.flute`
[jstyleparser](https://github.com/radkovo/jStyleParser/) |  `3.5` | `net.sf.cssbox`
[ph-css](https://github.com/phax/ph-css) | `6.2.3` |  `com.helger.css` | :warning: Currently unsupported because of Jacoco Error: "Method too large: com/helger/css/parser/ParserCSS30TokenManager.jjMoveNfa_0 (II)I"

#### INI
Project | Version | Instrumented Package
---     | --- | ---
[fastini](https://github.com/onlynight/FastIni) | `1.2.1` | `com.github.onlynight.fastini`
[ini4j](http://ini4j.sourceforge.net/) | `0.5.4` | `org.ini4j`
[java-configparser](https://github.com/ASzc/java-configparser) | `0.2` | `ca.szc.configparser`

#### Dot
Project | Version | Instrumented Package
---     | --- | ---
[digraph-parser](https://github.com/paypal/digraph-parser/) | `1.0` | `com.paypal.digraph.parser`
[graphstream](http://graphstream-project.org/) | `2.0` | `org.graphstream`
[graphviz-java](https://github.com/nidi3/graphviz-java/) | `0.17.0` | `guru.nidi`
