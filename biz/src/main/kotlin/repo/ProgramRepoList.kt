package ru.artursitnikov.fitness.biz.repo

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.repo.ProgramDbIdRequest
import ru.artursitnikov.fitness.common.repo.ProgramDbRequest
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.repoList(title: String) = worker {
    this.title = title
    description = "Чтение программ из БД"
    on { state == ContextState.RUNNING }
    handle {
        val result = repo.read()
        val resultPrograms = result.data
        if (result.isSuccess && resultPrograms != null) {
            listRepoDone = resultPrograms
        } else {
            state = ContextState.FAILING
            errors.addAll(result.errors)
        }
    }
}