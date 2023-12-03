import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.9.21"
  kotlin("plugin.serialization") version "1.9.21"
}

group = "me.kalvatn"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    apiVersion = "1.9"
    languageVersion = "1.9"
    suppressWarnings = true
  }
}
