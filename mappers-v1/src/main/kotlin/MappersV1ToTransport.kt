package ru.artursitnikov.fitness.mappers.v1

import exceptions.UnknownProgramCommand
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.*
import ru.artursitnikov.fitness.api.v1.models.Error as ApiError

fun ProgramContext.toTransport(): IResponse = when(val cmd = command) {
    ProgramCommand.CREATE -> toTransportCreate()
    ProgramCommand.READ -> toTransportRead()
    ProgramCommand.UPDATE -> toTransportUpdate()
    ProgramCommand.DELETE -> toTransportDelete()
    ProgramCommand.LIST -> toTransportList()
    ProgramCommand.NONE -> throw UnknownProgramCommand(cmd)
}

fun ProgramContext.toTransportCreate() = ProgramCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    program = programResponse.toTransport()
)

fun ProgramContext.toTransportRead() = ProgramReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    program = programResponse.toTransport()
)

fun ProgramContext.toTransportUpdate() = ProgramUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    program = programResponse.toTransport()
)

fun ProgramContext.toTransportDelete() = ProgramDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    program = programResponse.toTransport()
)

fun ProgramContext.toTransportList() = ProgramListResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    list = programListResponse.toTransport()
)

private fun List<Program>.toTransport() =
    this.map { it.toTransport() }.takeIf { it.isNotEmpty() }

private fun Program.toTransport() = ProgramResponseObject(
    id = id.takeIf { it != ProgramId.NONE }?.asLong(),
    ownerId = ownerId.takeIf { it != UserId.NONE }?.asLong(),
    clientId = clientId.takeIf { it != UserId.NONE }?.asLong(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
)

private fun List<GeneralError>.toTransportErrors(): List<ApiError>? =
    this.map { it.toTransport() }.takeIf { it.isNotEmpty() }

private fun GeneralError.toTransport() = ApiError(
    code = code.takeIf { code.isNotBlank() },
    group = group.takeIf { group.isNotBlank() },
    field = field.takeIf { field.isNotBlank() },
    message = message.takeIf { message.isNotBlank() },
)