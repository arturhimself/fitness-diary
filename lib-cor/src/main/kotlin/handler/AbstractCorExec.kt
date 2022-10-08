package ru.artursitnikov.fitness.lib.cor.handler
import ru.artursitnikov.fitness.lib.cor.ICorExec
import ru.artursitnikov.fitness.lib.cor.ICorExecDsl

abstract class AbstractCorExec<T>(
    override val title: String,
    override val description: String = "",
    private val blockOn: suspend T.() -> Boolean = { true },
    private val blockExcept: suspend T.(Throwable) -> Unit = {},
): ICorExec<T> {
    protected abstract suspend fun handle(context: T)

    private suspend fun on(context: T): Boolean = context.blockOn()
    private suspend fun except(context: T, e: Throwable) = context.blockExcept(e)

    override suspend fun exec(context: T) {
        if (on(context)) {
            runCatching {
                handle(context)
            }.onFailure {
                except(context, it)
            }
        }
    }
}

abstract class CorExecDsl<T> : ICorExecDsl<T> {
    protected var blockOn: suspend T.() -> Boolean = { true }
    protected var blockException: suspend T.(e: Throwable) -> Unit = { e: Throwable -> throw e }

    override var title: String = ""
    override var description: String = ""

    override fun on(function: suspend T.() -> Boolean) {
        blockOn = function
    }

    override fun exception(function: suspend T.(e: Throwable) -> Unit) {
        blockException = function
    }
}