package ru.artursitnikov.fitness.biz.stubs

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.stubs.ProgramStubs
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker
import ru.artursitnikov.fitness.stubs.ProgramStub

fun ICorChainDsl<ProgramContext>.stubListSuccess(title: String) = worker {
    this.title = title
    on { stubCase == ProgramStubs.SUCCESS && state == ContextState.RUNNING }
    handle {
        state = ContextState.FINISHING
        val stub = ProgramStub.getList()
        programListResponse = stub
    }
}