plugins {
    kotlin("jvm")
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