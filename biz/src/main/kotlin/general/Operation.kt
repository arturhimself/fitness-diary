package ru.artursitnikov.fitness.biz.general

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.models.ProgramCommand
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.chain

fun ICorChainDsl<ProgramContext>.operation(title: String, command: ProgramCommand, block: ICorChainDsl<ProgramContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == ContextState.RUNNING }
}