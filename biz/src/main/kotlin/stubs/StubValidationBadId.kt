package ru.artursitnikov.fitness.biz.stubs

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.models.GeneralError
import ru.artursitnikov.fitness.common.stubs.ProgramStubs
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == ProgramStubs.BAD_ID && state == ContextState.RUNNING }
    handle {
        state = ContextState.FAILING
        this.errors.add(
            GeneralError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}