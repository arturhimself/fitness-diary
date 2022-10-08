package ru.artursitnikov.fitness.lib.cor.handler

import ru.artursitnikov.fitness.lib.cor.CorDslMarker
import ru.artursitnikov.fitness.lib.cor.ICorExec
import ru.artursitnikov.fitness.lib.cor.ICorWorkerDsl

class CorWorker<T>(
    override val title: String,
    override val description: String = "",
    val blockOn: suspend T.() -> Boolean = { true },
    val blockHandle: suspend T.() -> Unit,
    val blockException: suspend T.(e: Throwable) -> Unit = { e -> throw e }
) : ICorExec<T> {
    override suspend fun exec(context: T) {
        if (context.blockOn()) {
            runCatching {
                context.blockHandle()
            }.onFailure {
                context.blockException(it)
            }
        }
    }
}

@CorDslMarker
class CorWorkerDsl<T> : CorExecDsl<T>(), ICorWorkerDsl<T> {
    private var blockHandle: suspend T.() -> Unit = {}

    override fun handle(function: suspend T.() -> Unit) {
        blockHandle = function
    }

    override fun build(): ICorExec<T> = CorWorker(
        title = title,
        description = description,
        blockOn = blockOn,
        blockHandle = blockHandle,
        blockException = blockException
    )
}