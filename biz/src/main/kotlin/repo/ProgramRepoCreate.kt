package ru.artursitnikov.fitness.biz.repo

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.repo.ProgramDbRequest
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление программы в БД"
    on { state == ContextState.RUNNING }
    handle {
        val request = ProgramDbRequest(repoPrepare)
        val result = repo.create(request)
        val resultProgram = result.data
        if (result.isSuccess && resultProgram != null) {
            repoDone = resultProgram
        } else {
            state = ContextState.FAILING
            errors.addAll(result.errors)
        }
    }
}
