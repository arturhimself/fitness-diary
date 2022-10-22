plugins {
    kotlin("jvm")
}

dependencies {
    val springKafkaVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project
    val coroutinesVersion: String by project

    implementation(project(":api-v1"))
    implementation(project(":biz"))
    implementation(project(":common"))
    implementation(project(":mappers-v1"))

    implementation("org.springframework.kafka:spring-kafka:$springKafkaVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")

    testImplementation(kotlin("test"))
}