package ru.artursitnikov.fitness.common.models

data class Program(
    var id: ProgramId = ProgramId.NONE,
    var ownerId: UserId = UserId.NONE,
    var clientId: UserId = UserId.NONE,
    var title: String = "",
    var description: String = "",
) {
    fun deepCopy() = copy()
}