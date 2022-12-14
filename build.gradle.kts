import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
}

group = "one.devsky"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))

    // Reflection Dependencies for automatic registration of commands and listeners
    implementation("net.oneandone.reflections8:reflections8:0.11.7")

    implementation("io.github.cdimascio:dotenv-kotlin:6.4.0")
    implementation("net.dv8tion:JDA:5.0.0-beta.2")
    implementation("com.github.TheFruxz:Ascend:11.0.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}