package ru.artursitnikov.fitness.repo.tests

import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.models.ProgramLock
import ru.artursitnikov.fitness.common.models.UserId

abstract class BaseInitAds() : InitObjects<Program> {
    open val lockOld: ProgramLock = ProgramLock("20000000-0000-0000-0000-000000000001")

    open val lockBad: ProgramLock = ProgramLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: UserId = UserId(1),
        lock: ProgramLock = lockOld,
        id: ProgramId = ProgramId(1)
    ) = Program(
        id = id,
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        lock = lock,
    )
}
