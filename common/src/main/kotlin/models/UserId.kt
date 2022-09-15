package ru.artursitnikov.fitness.common.models

@JvmInline
value class UserId(private val id: Long) {
    fun asString(): String = id.toString()
    fun asLong(): Long = id

    companion object {
        val NONE = UserId(0L)
    }
}