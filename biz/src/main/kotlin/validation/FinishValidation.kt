package ru.artursitnikov.fitness.biz.validation

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.finishProgramValidation(title: String) = worker {
    this.title = title
    on { state == ContextState.RUNNING }
    handle {
        programValidated = programValidating
    }
}