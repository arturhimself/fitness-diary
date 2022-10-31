package ru.artursitnikov.fitness.common.helpers

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.exceptions.RepoConcurrencyException
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.models.GeneralError
import ru.artursitnikov.fitness.common.models.ProgramLock

fun ProgramContext.addError(vararg error: GeneralError) = errors.addAll(error)

fun ProgramContext.fail(vararg error: GeneralError) {
    addError(*error)
    state = ContextState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: GeneralError.Levels = GeneralError.Levels.ERROR,
) = GeneralError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun Throwable.asGeneralError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = GeneralError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: GeneralError.Levels = GeneralError.Levels.ERROR,
) = GeneralError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)

fun errorRepoConcurrency(
    expectedLock: ProgramLock,
    actualLock: ProgramLock?,
    exception: Exception? = null,
) = GeneralError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)
