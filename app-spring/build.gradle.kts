import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	val springdocOpenapiUiVersion: String by project
	val coroutinesVersion: String by project
	val springKafkaVersion: String by project

	implementation(project(":api-v1"))
	implementation(project(":mappers-v1"))
	implementation(project(":common"))
	implementation(project(":stubs"))
	implementation(project(":biz"))
	implementation(project(":kafka"))
	implementation(project(":repo-inmemory"))
	implementation(project(":repo-postgres"))

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springdoc:springdoc-openapi-ui:$springdocOpenapiUiVersion")
	implementation("org.springframework.kafka:spring-kafka:$springKafkaVersion")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${coroutinesVersion}")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${coroutinesVersion}")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core:4.8.0")
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
	testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks {
	@Suppress("UnstableApiUsage")
	withType<ProcessResources> {
		from("$rootDir/specs") {
			into("/static")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
