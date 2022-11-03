package ru.artursitnikov.fitness.repo.postgres

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.artursitnikov.fitness.common.models.*
import ru.artursitnikov.fitness.common.repo.*
import ru.artursitnikov.fitness.repo.postgres.tables.ProgramTable
import ru.artursitnikov.fitness.repo.postgres.tables.UserTable
import java.sql.SQLException
import java.util.NoSuchElementException

class ProgramRepoSql(
    config: DbConfig,
    initObjects: Collection<Program> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : ProgramRepository {
    private val db by lazy { SqlConnector(config).connect(ProgramTable, UserTable) }

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(program: Program): ProgramDbResponse =
        safeTransaction({
            val actualOwnerId = UserTable.insertIgnoreAndGetId {
                if (program.ownerId != UserId.NONE) {
                    it[id] = program.ownerId.asLong()
                }
            }

            val actualClientId = UserTable.insertIgnoreAndGetId {
                if (program.clientId != UserId.NONE) {
                    it[id] = program.clientId.asLong()
                }
            }

            val result = ProgramTable.insert {
                if (program.id != ProgramId.NONE) {
                    it[id] = program.id.asLong()
                }
                it[title] = program.title
                it[description] = program.description
                it[ownerId] = actualOwnerId
                it[clientId] = actualClientId
                it[lock] = program.lock.asString()
            }

            ProgramDbResponse(
                data = ProgramTable.from(result),
                isSuccess = true
            )
        }, {
            ProgramDbResponse(
                data = null,
                isSuccess = false,
                errors = listOf(GeneralError(
                    code = "db-error",
                    message = message ?: localizedMessage
                ))
            )
        })

    override suspend fun create(request: ProgramDbRequest): ProgramDbResponse {
        val program = request.program.copy(lock = ProgramLock(randomUuid()))
        return save(program)
    }

    override suspend fun read(): ProgramListDbResponse =
        safeTransaction({
            val result = ProgramTable.selectAll().limit(10, offset = 0)
            ProgramListDbResponse(
                data = result.map { ProgramTable.from(it) },
                isSuccess = true
            )
        }, {
            ProgramListDbResponse(data = null, isSuccess = false, errors = listOf(GeneralError(message = localizedMessage)))
        })

    override suspend fun read(request: ProgramDbIdRequest): ProgramDbResponse =
        safeTransaction({
            val result = ProgramTable.select { ProgramTable.id eq request.id.asLong() }.single()
            ProgramDbResponse(
                data = ProgramTable.from(result),
                isSuccess = true
            )
        }, {
            val err = when (this) {
                is NoSuchElementException -> GeneralError(field = "id", message = "Not Found", code = "not-found")
                is IllegalArgumentException -> GeneralError(message = "More than one element with the same id")
                else -> GeneralError(message = localizedMessage)
            }
            ProgramDbResponse(data = null, isSuccess = false, errors = listOf(err))
        })

    override suspend fun update(request: ProgramDbRequest): ProgramDbResponse {
        val program = request.program
        val key = program.id.takeIf { it != ProgramId.NONE }?.asLong() ?: return resultErrorEmptyId
        val oldLock = program.lock.takeIf { it != ProgramLock.NONE }?.asString()
        val newProgram = program.copy().apply { lock = ProgramLock(randomUuid()) }

        return safeTransaction({
            val local = ProgramTable.select { ProgramTable.id eq key }.singleOrNull()?.let {
                ProgramTable.from(it)
            } ?: return@safeTransaction resultErrorNotFound(key.toString())

            return@safeTransaction when (oldLock) {
                null, local.lock.asString() -> updateDb(newProgram)
                else -> resultErrorConcurrent(oldLock, local)
            }
        }, {
            resultErrorNotFound(request.program.id.asString())
        })
    }

    override suspend fun delete(request: ProgramDbIdRequest): ProgramDbResponse {
        val key = request.id.takeIf { it != ProgramId.NONE }?.asLong() ?: return resultErrorEmptyId

        return safeTransaction({
            val local = ProgramTable.select { ProgramTable.id eq key }.single().let {
                ProgramTable.from(it)
            }

            if (local.lock == request.lock) {
                ProgramTable.deleteWhere { ProgramTable.id eq key }
                ProgramDbResponse(data = local, isSuccess = true)
            } else {
                resultErrorConcurrent(request.lock.toString(), local)
            }
        }, {
            resultErrorNotFound(request.id.asString())
        })
    }

    private fun updateDb(program: Program): ProgramDbResponse {
        ProgramTable.update({ ProgramTable.id eq program.id.asLong() }) {
            it[title] = program.title
            it[description] = program.description
            it[ownerId] = program.ownerId.takeIf { actualOwnerId -> actualOwnerId != UserId.NONE }?.asLong()
            it[clientId] = program.clientId.takeIf { actualClientId -> actualClientId != UserId.NONE }?.asLong()
            it[lock] = program.lock.asString()
        }

        val result = ProgramTable.select { ProgramTable.id eq program.id.asLong() }.single()

        return ProgramDbResponse(data = ProgramTable.from(result), isSuccess = true)
    }

    private fun <T>safeTransaction(statement: Transaction.() -> T, handleException: Throwable.() -> T): T =
        try {
            transaction(db, statement)
        } catch (e: SQLException) {
            throw e
        } catch (e: Throwable) {
            handleException(e)
        }
}