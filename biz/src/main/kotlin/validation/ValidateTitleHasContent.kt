package ru.artursitnikov.fitness.biz.validation

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.helpers.errorValidation
import ru.artursitnikov.fitness.common.helpers.fail
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.validateTitleHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { programValidating.title.isNotEmpty() && ! programValidating.title.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "title",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}