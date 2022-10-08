package ru.artursitnikov.fitness.lib.cor

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import ru.artursitnikov.fitness.lib.cor.handler.CorChain
import ru.artursitnikov.fitness.lib.cor.handler.CorWorker
import ru.artursitnikov.fitness.lib.cor.handler.executeSequential
import kotlin.test.assertEquals

class CorBaseTest {

    @Test
    fun `worker should execute handle`() = runBlocking {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockHandle = { history += "w1" }
        )

        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("w1", ctx.history)
    }

    @Test
    fun `worker should not execute when off`() = runBlocking {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockOn = { status == CorStatus.ERROR },
            blockHandle = { history += "w1" }
        )

        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("", ctx.history)
    }

    @Test
    fun `worker should handle exception`() = runBlocking {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockHandle = { throw RuntimeException("some error") },
            blockException = { e -> history += e.message },
        )

        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("some error", ctx.history)
    }

    @Test
    fun `chain should execute workers`() = runBlocking {
        val createWorker = { title: String ->
            CorWorker<TestContext>(
                title = title,
                blockOn = { status == CorStatus.NONE },
                blockHandle = { history += "$title; " }
            )
        }
        val chain = CorChain<TestContext>(
            execs = listOf(createWorker("w1"), createWorker("w2")),
            title = "chain",
            handler = ::executeSequential
        )

        val ctx = TestContext()
        chain.exec(ctx)
        assertEquals("w1; w2; ", ctx.history)
    }
}

data class TestContext(
    var status: CorStatus = CorStatus.NONE,
    var some: Int = 0,
    var history: String = ""
)

enum class CorStatus {
    NONE,
    RUNNING,
    FAILING,
    DONE,
    ERROR
}