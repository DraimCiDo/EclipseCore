plugins {
    kotlin("jvm") version "1.9.23"
}

group = "me.draimgoose"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}