package ru.artursitnikov.fitness.stubs

import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.models.UserId

object ProgramStub {
    fun get() = Program(
        id = ProgramId(1),
        ownerId = UserId(1),
        clientId = UserId(2),
        title = "Muscle growth",
        description = "Program for growing muscles"
    )

    fun getList() = mutableListOf(
        getProgramForList(id = ProgramId(1), clientId = UserId(2)),
        getProgramForList(id = ProgramId(2), clientId = UserId(3)),
        getProgramForList(id = ProgramId(3), clientId = UserId(4)),
    )

    private fun getProgramForList(id: ProgramId, clientId: UserId) = get().apply {
        this.id = id
        this.clientId = clientId
    }

    fun prepareResult(block: Program.() -> Unit): Program = get().apply(block)
}