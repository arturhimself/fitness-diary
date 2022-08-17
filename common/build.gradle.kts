plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation(kotlin("test-unit"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}