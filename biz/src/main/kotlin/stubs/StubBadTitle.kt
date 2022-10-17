package ru.artursitnikov.fitness.biz.stubs

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.helpers.fail
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.models.GeneralError
import ru.artursitnikov.fitness.common.stubs.ProgramStubs
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.stubValidationBadTitle(title: String) = worker {
    this.title = title
    on { stubCase == ProgramStubs.BAD_TITLE && state == ContextState.RUNNING }
    handle {
        fail(
            GeneralError(
                group = "validation",
                code = "validation-title",
                field = "title",
                message = "Wrong title field"
            )
        )
    }
}