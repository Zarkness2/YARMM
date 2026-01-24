plugins {
    kotlin("jvm") version libs.versions.kotlin
}

group = "io.tanguygab"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.conditionalactions)
    compileOnly(files("../../dependencies/TAB-5.5.0.jar"))
}

kotlin {
    jvmToolchain(21)
}

tasks.processResources {
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand("version" to version)
    }
}
