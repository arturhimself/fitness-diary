package ru.artursitnikov.fitness.repo.postgres

data class DbConfig(
    val url: String = System.getenv("DATABASE_URL"),
    val user: String = System.getenv("DATABASE_USER"),
    val password: String = System.getenv("DATABASE_PASSWORD"),
    val schema: String = System.getenv("DATABASE_SCHEMA"),
    val driver: String = "org.postgresql.Driver",
)