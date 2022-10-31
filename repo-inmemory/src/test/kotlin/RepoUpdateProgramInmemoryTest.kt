package ru.artursitnikov.fitness.repo.inmemory

import ru.artursitnikov.fitness.repo.tests.RepoUpdateProgramTest

class RepoUpdateProgramInmemoryTest : RepoUpdateProgramTest() {
    override val repo = ProgramRepoInmemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
