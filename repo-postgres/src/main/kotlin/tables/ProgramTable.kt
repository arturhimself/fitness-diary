package ru.artursitnikov.fitness.repo.postgres.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.models.ProgramLock
import ru.artursitnikov.fitness.common.models.UserId

object ProgramTable : LongIdTable("programs") {
    val title = varchar("title", 100)
    val description = varchar("description", 255)
    val ownerId = reference("owner_id", UserTable.id).index().nullable()
    val clientId = reference("client_id", UserTable.id).index().nullable()
    val lock = varchar("lock", 100)

    fun from(result: InsertStatement<Number>): Program =
        Program(
            id = ProgramId(result[id].value),
            title = result[title],
            description = result[description],
            ownerId = result[ownerId]?.value?.let { UserId(it) } ?: UserId.NONE,
            clientId = result[clientId]?.value?.let { UserId(it) } ?: UserId.NONE,
            lock = ProgramLock(result[lock].toString()),
        )

    fun from(result: ResultRow): Program =
        Program(
            id = ProgramId(result[id].value),
            title = result[title],
            description = result[description],
            ownerId = result[ownerId]?.value?.let { UserId(it) } ?: UserId.NONE,
            clientId = result[clientId]?.value?.let { UserId(it) } ?: UserId.NONE,
            lock = ProgramLock(result[lock].toString()),
        )
}