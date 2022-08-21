import exceptions.UnknownCoachCommand
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.common.CoachContext
import ru.artursitnikov.fitness.common.models.*
import ru.artursitnikov.fitness.api.v1.models.Error as ApiError

fun CoachContext.toTransport(): IResponse = when(val cmd = command) {
    CoachCommand.CREATE -> toTransportCreate()
    CoachCommand.READ -> toTransportRead()
    CoachCommand.UPDATE -> toTransportUpdate()
    CoachCommand.DELETE -> toTransportDelete()
    CoachCommand.LIST -> toTransportList()
    CoachCommand.NONE -> throw UnknownCoachCommand(cmd)
}

fun CoachContext.toTransportCreate() = CoachCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    coach = coachResponse.toTransport()
)

fun CoachContext.toTransportRead() = CoachReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    coach = coachResponse.toTransport()
)

fun CoachContext.toTransportUpdate() = CoachUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    coach = coachResponse.toTransport()
)

fun CoachContext.toTransportDelete() = CoachDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    coach = coachResponse.toTransport()
)

fun CoachContext.toTransportList() = CoachListResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContextState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    list = coachListResponse.toTransport()
)

private fun List<Coach>.toTransport() =
    this.map { it.toTransport() }.takeIf { it.isNotEmpty() }

private fun Coach.toTransport() = CoachResponseObject(
    id = id.takeIf { it != CoachId.NONE }?.asLong(),
    email = email.takeIf { it.isNotBlank() },
    password = password.takeIf { it.isNotBlank() },
    name = name.takeIf { it.isNotBlank() },
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