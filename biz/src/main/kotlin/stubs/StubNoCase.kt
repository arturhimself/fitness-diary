package ru.artursitnikov.fitness.biz.stubs

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.helpers.fail
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.models.GeneralError
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == ContextState.RUNNING }
    handle {
        fail(
            GeneralError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}