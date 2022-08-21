package ru.artursitnikov.fitness.common.models

data class Coach(
    var id: CoachId = CoachId.NONE,
    var email: String = "",
    var password: String = "",
    var name: String = "",
    val description: String = ""
)