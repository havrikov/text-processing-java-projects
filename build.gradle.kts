import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version Deps.kotlinVersion apply false
    id("com.github.johnrengelman.shadow") version Deps.shadowVersion apply false
}

allprojects {
    group = "saarland.cispa.se"
    version = "0.1"
}

// apply kotlin to all sub-projects
subprojects {
    repositories {
        jcenter()
    }

    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        add("implementation", kotlin("stdlib"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

val specialProjects = setOf("utils")
val jacocoInstrumented = Attribute.of("jacoco.instrumented", Boolean::class.javaObjectType)
configure(subprojects.filter { it.name !in specialProjects }) {
    val subject by configurations.creating {
        isVisible = false
        isTransitive = false
        isCanBeConsumed = false
        isCanBeResolved = true
    }

    configurations.getByName("compileOnly") {
        extendsFrom(subject)
    }

    val instrumented by configurations.creating {
        isVisible = false
        isTransitive = false
        isCanBeConsumed = false
        isCanBeResolved = true
        extendsFrom(subject)
        afterEvaluate {
            attributes.attribute(jacocoInstrumented, true)
        }
    }

    dependencies {
        attributesSchema {
            attribute(jacocoInstrumented)
        }
        artifactTypes.getByName("jar") {
            attributes.attribute(jacocoInstrumented, false)
        }
        registerTransform(JacocoJarInstrumenter::class) {
            from.attribute(jacocoInstrumented, false)
            to.attribute(jacocoInstrumented, true)
        }
        // also add dependency in the utils project
        add("implementation", project(":utils"))
    }

    // apply shadow plugin for subject sub-projects for easy packaging
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<ShadowJar> {
        archiveClassifier.set("subject")
        archiveVersion.set("")
        manifest.attributes["Main-Class"] = "saarland.cispa.subjects.Driver"
        configurations = configurations + listOf(instrumented)
    }

    tasks.getByName("build").dependsOn("shadowJar")

    tasks.withType<Jar> {
        if (this !is ShadowJar) {
            enabled = false
        }
    }

    // a task for downloading all un-instrumented subjects for analysis with jacoco
    tasks.register<Copy>("downloadOriginalJar") {
        from(subject)
        into("$buildDir/original")
    }
}

// copy all created jars into one directory for convenience
val gatherArtifacts = tasks.register<Copy>("gatherArtifacts") {
    val artifacts = subprojects.asSequence().filter { it.name !in specialProjects }.map { it.tasks.getByName("shadowJar") }.toList()
    dependsOn(artifacts)
    from(artifacts)
    into("$buildDir/libs")
}

// copy all original jars into one directory for convenience
val gatherOriginals = tasks.register<Copy>("gatherOriginals") {
    val originals = subprojects.asSequence().filter { it.name !in specialProjects }.map { it.tasks.getByName("downloadOriginalJar") }.toList()
    dependsOn(originals)
    from(originals)
    into("$buildDir/originals")
}

tasks.getByName("build") {
    dependsOn(gatherArtifacts)
    dependsOn(gatherOriginals)

    // generate the README file with the subject versions used
    dependsOn(tasks.register<ReadmeGenerationTask>("generateReadme"))
}
