package ru.artursitnikov.fitness.repo.inmemory

import ru.artursitnikov.fitness.repo.tests.RepoReadProgramTest

class RepoReadProgramInmemoryTest : RepoReadProgramTest() {
    override val repo = ProgramRepoInmemory(
        initObjects = initObjects
    )
}
