package ru.artursitnikov.fitness.common.models

import ru.artursitnikov.fitness.common.repo.ProgramRepository

data class Settings(
    val repoTest: ProgramRepository = ProgramRepository.NONE,
    val repoStub: ProgramRepository = ProgramRepository.NONE,
    val repoProd: ProgramRepository = ProgramRepository.NONE,
)