plugins {
    kotlin("jvm")
}

dependencies {
    val cache4kVersion: String by project
    val coroutinesVersion: String by project
    val kmpUUIDVersion: String by project

    implementation(project(":common"))
    implementation(project(":repo-tests"))

    implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    testImplementation(kotlin("test"))
}