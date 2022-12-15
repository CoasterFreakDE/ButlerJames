import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "one.devsky"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

val shadowDependencies = listOf(
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4",
    "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1",
    "net.oneandone.reflections8:reflections8:0.11.7",
    "io.github.cdimascio:dotenv-kotlin:6.4.0",
    "net.dv8tion:JDA:5.0.0-beta.2",
    "com.github.TheFruxz:Ascend:11.0.0"
)

dependencies {
    testImplementation(kotlin("test"))

    // Kotlin Base Dependencies
    ("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5").let { dependency ->
        implementation(dependency)
        shadow(dependency) { isTransitive = false } // <- non-transitive is important
    }

    shadowDependencies.forEach { dependency ->
        implementation(dependency)
        shadow(dependency)
    }
}

tasks {

    test {
        useJUnitPlatform()
    }

    build {
        dependsOn("shadowJar")
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

    withType<ShadowJar> {
        mergeServiceFiles()
        configurations = listOf(project.configurations.shadow.get())
        archiveFileName.set("ButlerJames.jar")

        manifest {
            attributes["Main-Class"] = "one.devsky.butlerjames.StartKt"
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    }

}