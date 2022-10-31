package ru.artursitnikov.fitness.repo.inmemory

import ru.artursitnikov.fitness.repo.tests.RepoCreateProgramTest

class RepoCreateProgramInmemoryTest : RepoCreateProgramTest() {
    override val repo = ProgramRepoInmemory(
        randomUuid = { lockNew.asString() }
    )
}
