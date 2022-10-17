package ru.artursitnikov.fitness.biz.validation

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.chain

fun ICorChainDsl<ProgramContext>.validation(block: ICorChainDsl<ProgramContext>.() -> Unit) = chain {
    block()
    title = "Валидация"
    on { state == ContextState.RUNNING }
}