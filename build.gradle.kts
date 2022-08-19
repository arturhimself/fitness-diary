plugins {
    kotlin("jvm") apply false

    id("org.openapi.generator") apply false
}

group = "ru.artursitnikov"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}