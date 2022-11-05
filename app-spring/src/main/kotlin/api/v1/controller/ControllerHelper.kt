package ru.artursitnikov.fitness.appspring.api.v1.controller

import kotlinx.datetime.Clock
import ru.artursitnikov.fitness.api.v1.models.IRequest
import ru.artursitnikov.fitness.api.v1.models.IResponse
import ru.artursitnikov.fitness.biz.ProgramProcessor
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.helpers.asGeneralError
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.models.ProgramCommand
import ru.artursitnikov.fitness.mappers.v1.fromTransport
import ru.artursitnikov.fitness.mappers.v1.toTransport

suspend inline fun <reified Q : IRequest, reified R : IResponse> processV1(
    processor: ProgramProcessor,
    command: ProgramCommand? = null,
    request: Q,
): R {
    val context = ProgramContext(
        timeStart = Clock.System.now(),
    )
    return try {
        context.fromTransport(request)
        processor.exec(context)
        context.toTransport() as R
    } catch (e: Throwable) {
        command?.also { context.command = it }
        context.state = ContextState.FAILING
        context.errors.add(e.asGeneralError())
        processor.exec(context)
        context.toTransport() as R
    }
}