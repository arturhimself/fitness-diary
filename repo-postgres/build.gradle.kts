plugins {
    kotlin("jvm")
}

dependencies {
    val testContainersVersion: String by project
    val exposedVersion: String by project
    val postgresDriverVersion: String by project
    val kmpUUIDVersion: String by project

    implementation(project(":common"))
    implementation(project(":repo-tests"))

    implementation("org.postgresql:postgresql:$postgresDriverVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    testImplementation(kotlin("test"))
}

tasks {
    withType<Test> {
        environment("DATABASE_URL", "jdbc:postgresql://localhost:5432/fitness")
        environment("DATABASE_USER", "postgres")
        environment("DATABASE_PASSWORD", "postgres")
        environment("DATABASE_SCHEMA", "public")
        environment("DROP_DB", true)
    }
}