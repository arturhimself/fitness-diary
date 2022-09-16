package ru.artursitnikov.fitness.mappers.v1

import exceptions.UnknownRequestClass
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.*
import ru.artursitnikov.fitness.common.stubs.ProgramStubs

fun ProgramContext.fromTransport(request: IRequest) = when (request) {
    is ProgramCreateRequest -> fromTransport(request)
    is ProgramReadRequest -> fromTransport(request)
    is ProgramUpdateRequest -> fromTransport(request)
    is ProgramDeleteRequest -> fromTransport(request)
    is ProgramListRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass, this.javaClass)
}

fun ProgramContext.fromTransport(request: ProgramCreateRequest) {
    command = ProgramCommand.CREATE
    requestId = request.requestId()
    programRequest = request.program?.toInternal() ?: Program()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProgramContext.fromTransport(request: ProgramReadRequest) {
    command = ProgramCommand.READ
    requestId = request.requestId()
    programRequest = request.program?.id.toProgramWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProgramContext.fromTransport(request: ProgramUpdateRequest) {
    command = ProgramCommand.UPDATE
    requestId = request.requestId()
    programRequest = request.program?.toInternal() ?: Program()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProgramContext.fromTransport(request: ProgramDeleteRequest) {
    command = ProgramCommand.DELETE
    requestId = request.requestId()
    programRequest = request.program?.id.toProgramWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProgramContext.fromTransport(request: ProgramListRequest) {
    command = ProgramCommand.READ
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun Long?.toProgramId() = this?.let { ProgramId(it) } ?: ProgramId.NONE
private fun Long?.toProgramWithId() = Program(id = this.toProgramId())
private fun IRequest.requestId() = this.requestId?.let { RequestId(it) } ?: RequestId.NONE

private fun ProgramDebug?.transportToStubCase(): ProgramStubs = when(this?.stub) {
    ProgramRequestDebugStubs.SUCCESS -> ProgramStubs.SUCCESS
    ProgramRequestDebugStubs.NOT_FOUND -> ProgramStubs.NOT_FOUND
    ProgramRequestDebugStubs.BAD_ID -> ProgramStubs.BAD_ID
    ProgramRequestDebugStubs.BAD_TITLE -> ProgramStubs.BAD_TITLE
    ProgramRequestDebugStubs.CANNOT_DELETE -> ProgramStubs.CANNOT_DELETE
    null -> ProgramStubs.NONE
}

private fun ProgramDebug?.transportToWorkMode(): WorkMode = when(this?.mode) {
    ProgramRequestDebugMode.PROD -> WorkMode.PROD
    ProgramRequestDebugMode.TEST -> WorkMode.TEST
    ProgramRequestDebugMode.STUB -> WorkMode.STUB
    null -> WorkMode.PROD
}

private fun ProgramCreateObject.toInternal() = Program(
    title = this.title ?: "",
    description = this.description ?: "",
)

private fun ProgramUpdateObject.toInternal() = Program(
    ownerId = this.ownerId?.let { UserId(this.ownerId!!) } ?: UserId.NONE,
    clientId = this.clientId?.let { UserId(this.clientId!!) } ?: UserId.NONE,
    title = this.title ?: "",
    description = this.description ?: "",
)