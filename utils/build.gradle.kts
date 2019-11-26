buildscript {
    apply(from = "$rootDir/buildSrc/versions.gradle.kts")
}

val jacocoVersion: String by extra
dependencies {
    implementation("com.xenomachina", "kotlin-argparser", Deps.argparserVersion)
    implementation("org.jacoco", "org.jacoco.core", jacocoVersion)
    implementation("org.jacoco", "org.jacoco.agent", jacocoVersion, classifier = "runtime")
    implementation("com.opencsv", "opencsv", Deps.openCsvVersion)
    implementation("net.logstash.logback", "logstash-logback-encoder", Deps.logstashVersion)

    implementation("javax.json", "javax.json-api", Deps.javaxJsonVersion)
    runtimeOnly("org.glassfish", "javax.json", Deps.javaxJsonVersion)

    implementation("io.github.microutils", "kotlin-logging", Deps.kotlinLoggingVersion)
    runtimeOnly("ch.qos.logback", "logback-classic", Deps.logbackVersion)
}
