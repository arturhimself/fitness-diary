package ru.artursitnikov.fitness.biz.general

import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.helpers.errorAdministration
import ru.artursitnikov.fitness.common.helpers.fail
import ru.artursitnikov.fitness.common.models.WorkMode
import ru.artursitnikov.fitness.common.repo.ProgramRepository
import ru.artursitnikov.fitness.lib.cor.ICorChainDsl
import ru.artursitnikov.fitness.lib.cor.worker

fun ICorChainDsl<ProgramContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        repo = when (workMode) {
            WorkMode.TEST -> settings.repoTest
            WorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != WorkMode.STUB && repo == ProgramRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                "Please, contact the administrator staff"
            )
        )
    }
}