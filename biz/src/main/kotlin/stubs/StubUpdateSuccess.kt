package ru.artursitnikov.fitness.biz.stubs

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.stubs.ProgramStubs
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker
import ru.artursitnikov.fitness.stubs.ProgramStub

fun ICorChainDsl<ProgramContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == ProgramStubs.SUCCESS && state == ContextState.RUNNING }
    handle {
        state = ContextState.FINISHING
        val stub = ProgramStub.prepareResult {
            programRequest.id.takeIf { it != ProgramId.NONE }?.also { this.id = it }
            programRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            this.description = programRequest.description
            this.clientId = programRequest.clientId
            this.ownerId = programRequest.ownerId
        }
        programResponse = stub
    }
}