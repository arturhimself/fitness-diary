package ru.artursitnikov.fitness.repo.inmemory

import ru.artursitnikov.fitness.repo.tests.RepoDeleteProgramTest

class RepoDeleteProgramInmemoryTest : RepoDeleteProgramTest() {
    override val repo = ProgramRepoInmemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
