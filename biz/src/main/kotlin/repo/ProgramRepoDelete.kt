package ru.artursitnikov.fitness.biz.repo

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.repo.ProgramDbIdRequest
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление программы из БД по ID"
    on { state == ContextState.RUNNING }
    handle {
        val request = ProgramDbIdRequest(repoPrepare)
        val result = repo.delete(request)
        if (!result.isSuccess) {
            state = ContextState.FAILING
            errors.addAll(result.errors)
        }
        repoDone = repoRead
    }
}