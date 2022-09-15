package ru.artursitnikov.fitness.common.models

@JvmInline
value class ProgramId(private val id: Long) {
    fun asString(): String = id.toString()
    fun asLong(): Long = id

    companion object {
        val NONE = ProgramId(0L)
    }
}