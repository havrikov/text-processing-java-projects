buildscript {
    apply(from = "versions.gradle.kts")
}

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

val jacocoVersion: String by extra
dependencies {
    implementation("org.jacoco", "org.jacoco.core", jacocoVersion)
}
