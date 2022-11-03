package ru.artursitnikov.fitness.common.repo

import ru.artursitnikov.fitness.common.helpers.errorRepoConcurrency
import ru.artursitnikov.fitness.common.models.GeneralError
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramLock

val resultErrorEmptyId = ProgramDbResponse(
    data = null,
    isSuccess = false,
    errors = listOf(
        GeneralError(
            code = "id-empty",
            group = "validation",
            field = "id",
            message = "Id must not be null"
        )
    )
)

val resultErrorEmptyLock = ProgramDbResponse(
    data = null,
    isSuccess = false,
    errors = listOf(
        GeneralError(
            code = "lock-empty",
            group = "validation",
            field = "lock",
            message = "Lock must not be null or blank"
        )
    )
)

val resultErrorNotFound = fun (id: String) =
    ProgramDbResponse(
        isSuccess = false,
        data = null,
        errors = listOf(
            GeneralError(
                code = "not-found",
                field = "id",
                message = "Entity with id=$id not found"
            )
        )
    )

fun resultErrorConcurrent(lock: String, program: Program?) = ProgramDbResponse(
    data = program,
    isSuccess = false,
    errors = listOf(
        errorRepoConcurrency(ProgramLock(lock), program?.lock?.let { ProgramLock(it.asString()) }
        )
    ))