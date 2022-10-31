package ru.artursitnikov.fitness.biz.general

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.models.WorkMode
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != WorkMode.STUB }
    handle {
        programResponse = repoDone
        programListResponse = listRepoDone
        state = when (val st = state) {
            ContextState.RUNNING -> ContextState.FINISHING
            else -> st
        }
    }
}
