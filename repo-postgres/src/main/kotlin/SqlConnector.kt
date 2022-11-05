package ru.artursitnikov.fitness.repo.postgres

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

class SqlConnector(
    private val dbConfig: DbConfig,
) {
    private val globalConnection = Database.connect(
        url = dbConfig.url,
        user = dbConfig.user,
        password = dbConfig.password,
        databaseConfig = DatabaseConfig { defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ }
    )

    fun connect(vararg tables: Table): Database {
        val (url, user, password, schema, driver) = dbConfig

        transaction(globalConnection) {
            connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS ${dbConfig.schema}", false).executeUpdate()
        }

        val connect = Database.connect(
            url, driver, user, password,
            databaseConfig = DatabaseConfig { defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ },
            setupConnection = { connection ->
                connection.transactionIsolation = Connection.TRANSACTION_REPEATABLE_READ
                connection.schema = schema
            }
        )

        transaction(connect) {
            if (System.getenv("DROP_DB").toBoolean()) {
                SchemaUtils.drop(*tables, inBatch = true)
                SchemaUtils.create(*tables, inBatch = true)
            } else {
                SchemaUtils.createMissingTablesAndColumns(*tables, inBatch = true)
            }
        }

        return connect
    }
}