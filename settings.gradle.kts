rootProject.name = "fitness"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openapiVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}

include("common")
include("api-v1")
include("mappers-v1")
