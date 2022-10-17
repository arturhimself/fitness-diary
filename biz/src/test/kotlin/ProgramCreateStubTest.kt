package ru.artursitnikov.fitness.biz

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.*
import ru.artursitnikov.fitness.common.stubs.ProgramStubs
import ru.artursitnikov.fitness.stubs.ProgramStub
import kotlin.test.assertEquals

class ProgramCreateStubTest {
    private val processor = ProgramProcessor()
    private val id = ProgramId(1)
    private val title = "Muscle growth"
    private val description = "Program for growing muscles"

    @Test
    fun create() = runBlocking {
        val context = ProgramContext(
            command = ProgramCommand.CREATE,
            state = ContextState.RUNNING,
            workMode = WorkMode.STUB,
            stubCase = ProgramStubs.SUCCESS,
            programRequest = Program(
                title = title,
                description = description
            )
        )

        processor.exec(context)

        assertEquals(ProgramStub.get().id, context.programResponse.id)
        assertEquals(title, context.programResponse.title)
        assertEquals(description, context.programResponse.description)
    }

    @Test
    fun badTitle() = runBlocking {
        val context = ProgramContext(
            command = ProgramCommand.CREATE,
            state = ContextState.RUNNING,
            workMode = WorkMode.STUB,
            stubCase = ProgramStubs.BAD_TITLE,
            programRequest = Program(
                id = id,
                title = "",
                description = description
            )
        )

        processor.exec(context)

        assertEquals("title", context.errors.firstOrNull()?.field)
        assertEquals("validation", context.errors.firstOrNull()?.group)
    }
}