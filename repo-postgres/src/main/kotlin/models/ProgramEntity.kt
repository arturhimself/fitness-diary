package ru.artursitnikov.fitness.repo.postgres.models

import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.models.ProgramLock
import ru.artursitnikov.fitness.common.models.UserId

data class ProgramEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val ownerId: Long? = null,
    val clientId: Long? = null,
    val lock: String? = null,
) {
    constructor(program: Program): this(
        id = program.id.asString(),
        title = program.title.takeIf { it.isNotBlank() },
        description = program.description,
        ownerId = program.ownerId.asLong(),
        clientId = program.clientId.asLong(),
        lock = program.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = Program(
        id = id?.let { ProgramId(id.toLong()) } ?: ProgramId.NONE,
        title = title ?: "",
        description = description ?: "",
        ownerId = ownerId?.let { UserId(ownerId) } ?: UserId.NONE,
        clientId = clientId?.let { UserId(clientId) } ?: UserId.NONE,
        lock = lock?.let { ProgramLock(lock) } ?: ProgramLock.NONE
    )
}