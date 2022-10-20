rootProject.name = "fitness"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openapiVersion: String by settings
        val springframeworkBootVersion: String by settings
        val springDependencyManagementVersion: String by settings
        val pluginJpa: String by settings

        kotlin("jvm") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        val pluginSpringVersion: String by settings
        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false
    }
}

include("common")
include("api-v1")
include("mappers-v1")
include("app-spring")
include("stubs")
include("biz")
include("kafka")
include("lib-cor")
