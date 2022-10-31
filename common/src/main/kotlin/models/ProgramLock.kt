package ru.artursitnikov.fitness.common.models

@JvmInline
value class ProgramLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ProgramLock("")
    }
}