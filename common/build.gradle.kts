plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project

    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
}