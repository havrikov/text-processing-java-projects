buildscript {
    apply(from = "versions.gradle.kts")
}

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    jcenter()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

val jacocoVersion: String by extra
dependencies {
    implementation("org.jacoco", "org.jacoco.core", jacocoVersion)
}
