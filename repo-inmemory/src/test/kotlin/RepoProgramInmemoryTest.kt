package ru.artursitnikov.fitness.repo.inmemory

import ru.artursitnikov.fitness.repo.tests.RepoCreateProgramTest
import ru.artursitnikov.fitness.repo.tests.RepoDeleteProgramTest
import ru.artursitnikov.fitness.repo.tests.RepoReadProgramTest
import ru.artursitnikov.fitness.repo.tests.RepoUpdateProgramTest

class RepoCreateProgramInmemoryTest : RepoCreateProgramTest() {
    override val repo = ProgramRepoInmemory(
        randomUuid = { lockNew.asString() }
    )
}

class RepoDeleteProgramInmemoryTest : RepoDeleteProgramTest() {
    override val repo = ProgramRepoInmemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}

class RepoReadProgramInmemoryTest : RepoReadProgramTest() {
    override val repo = ProgramRepoInmemory(
        initObjects = initObjects
    )
}

class RepoUpdateProgramInmemoryTest : RepoUpdateProgramTest() {
    override val repo = ProgramRepoInmemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
