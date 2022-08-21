import exceptions.UnknownRequestClass
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.common.CoachContext
import ru.artursitnikov.fitness.common.models.*
import ru.artursitnikov.fitness.common.stubs.CoachStubs

fun CoachContext.fromTransport(request: IRequest) = when (request) {
    is CoachCreateRequest -> fromTransport(request)
    is CoachReadRequest -> fromTransport(request)
    is CoachUpdateRequest -> fromTransport(request)
    is CoachDeleteRequest -> fromTransport(request)
    is CoachListRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass, this.javaClass)
}

fun CoachContext.fromTransport(request: CoachCreateRequest) {
    command = CoachCommand.CREATE
    requestId = request.requestId()
    coachRequest = request.coach?.toInternal() ?: Coach()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CoachContext.fromTransport(request: CoachReadRequest) {
    command = CoachCommand.READ
    requestId = request.requestId()
    coachRequest = request.coach?.id.toCoachWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CoachContext.fromTransport(request: CoachUpdateRequest) {
    command = CoachCommand.UPDATE
    requestId = request.requestId()
    coachRequest = request.coach?.toInternal() ?: Coach()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CoachContext.fromTransport(request: CoachDeleteRequest) {
    command = CoachCommand.DELETE
    requestId = request.requestId()
    coachRequest = request.coach?.id.toCoachWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CoachContext.fromTransport(request: CoachListRequest) {
    command = CoachCommand.READ
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun Long?.toCoachId() = this?.let { CoachId(it) } ?: CoachId.NONE
private fun Long?.toCoachWithId() = Coach(id = this.toCoachId())
private fun IRequest.requestId() = this.requestId?.let { RequestId(it) } ?: RequestId.NONE

private fun CoachDebug?.transportToStubCase(): CoachStubs = when(this?.stub) {
    CoachRequestDebugStubs.SUCCESS -> CoachStubs.SUCCESS
    CoachRequestDebugStubs.NOT_FOUND -> CoachStubs.NOT_FOUND
    CoachRequestDebugStubs.BAD_ID -> CoachStubs.BAD_ID
    CoachRequestDebugStubs.BAD_EMAIL -> CoachStubs.BAD_EMAIL
    CoachRequestDebugStubs.BAD_PASSWORD -> CoachStubs.BAD_PASSWORD
    CoachRequestDebugStubs.BAD_NAME -> CoachStubs.BAD_NAME
    CoachRequestDebugStubs.BAD_DESCRIPTION -> CoachStubs.BAD_DESCRIPTION
    CoachRequestDebugStubs.CANNOT_DELETE -> CoachStubs.CANNOT_DELETE
    null -> CoachStubs.NONE
}

private fun CoachDebug?.transportToWorkMode(): WorkMode = when(this?.mode) {
    CoachRequestDebugMode.PROD -> WorkMode.PROD
    CoachRequestDebugMode.TEST -> WorkMode.TEST
    CoachRequestDebugMode.STUB -> WorkMode.STUB
    null -> WorkMode.PROD
}

private fun CoachCreateObject.toInternal() = Coach(
    email = this.email ?: "",
    password = this.password ?: "",
    name = this.name ?: "",
    description = this.description ?: "",
)

private fun CoachUpdateObject.toInternal() = Coach(
    email = this.email ?: "",
    password = this.password ?: "",
    name = this.name ?: "",
    description = this.description ?: "",
)