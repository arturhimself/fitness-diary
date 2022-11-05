package ru.artursitnikov.fitness.biz.repo

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.repo.ProgramDbIdRequest
import ru.artursitnikov.fitness.common.repo.ProgramDbRequest
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == ContextState.RUNNING }
    handle {
        val request = ProgramDbRequest(
            repoPrepare.deepCopy().apply {
                this.title = programValidated.title
                description = programValidated.description
            }
        )
        val result = repo.update(request)
        val resultProgram = result.data
        if (result.isSuccess && resultProgram != null) {
            repoDone = resultProgram
        } else {
            state = ContextState.FAILING
            errors.addAll(result.errors)
            repoDone
        }
    }
}