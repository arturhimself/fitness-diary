import ru.artursitnikov.fitness.repo.tests.RepoCreateProgramTest
import ru.artursitnikov.fitness.repo.tests.RepoDeleteProgramTest
import ru.artursitnikov.fitness.repo.tests.RepoReadProgramTest
import ru.artursitnikov.fitness.repo.tests.RepoUpdateProgramTest

class RepoCreateProgramSqlTest : RepoCreateProgramTest() {
    override val repo = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoDeleteProgramSqlTest : RepoDeleteProgramTest() {
    override val repo = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoReadProgramSqlTest : RepoReadProgramTest() {
    override val repo = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoUpdateProgramSqlTest : RepoUpdateProgramTest() {
    override val repo = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() }
    )
}
