package ru.artursitnikov.fitness.biz.validation

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.helpers.errorValidation
import ru.artursitnikov.fitness.common.helpers.fail
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { programValidating.id.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}