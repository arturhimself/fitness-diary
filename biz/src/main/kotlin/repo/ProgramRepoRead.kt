package ru.artursitnikov.fitness.biz.repo

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.repo.ProgramDbIdRequest
import ru.artursitnikov.fitness.common.repo.ProgramDbRequest
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение программы из БД"
    on { state == ContextState.RUNNING }
    handle {
        val request = ProgramDbIdRequest(programValidated)
        val result = repo.read(request)
        val resultProgram = result.data
        if (result.isSuccess && resultProgram != null) {
            repoRead = resultProgram
        } else {
            state = ContextState.FAILING
            errors.addAll(result.errors)
        }
    }
}