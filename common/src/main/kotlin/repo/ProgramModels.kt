package ru.artursitnikov.fitness.common.repo

import ru.artursitnikov.fitness.common.models.GeneralError
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.models.ProgramLock

data class ProgramDbRequest(
    val program: Program
)

data class ProgramDbIdRequest(
    val id: ProgramId,
    val lock: ProgramLock = ProgramLock.NONE,
) {
    constructor(program: Program): this(program.id, program.lock)
}


interface DbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<GeneralError>
}

data class ProgramDbResponse(
    override val data: Program?,
    override val isSuccess: Boolean,
    override val errors: List<GeneralError> = mutableListOf()
) : DbResponse<Program>

data class ProgramListDbResponse(
    override val data: List<Program>?,
    override val isSuccess: Boolean,
    override val errors: List<GeneralError> = mutableListOf()
) : DbResponse<List<Program>>