package ru.artursitnikov.fitness.common.exceptions

import ru.artursitnikov.fitness.common.models.ProgramLock

class RepoConcurrencyException(expectedLock: ProgramLock, actualLock: ProgramLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)