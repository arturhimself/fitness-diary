package ru.artursitnikov.fitness.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.models.ProgramLock
import ru.artursitnikov.fitness.common.models.UserId
import ru.artursitnikov.fitness.common.repo.ProgramDbRequest
import ru.artursitnikov.fitness.common.repo.ProgramRepository
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCreateProgramTest {
    abstract val repo: ProgramRepository

    protected open val lockNew: ProgramLock = ProgramLock("20000000-0000-0000-0000-000000000001")

    private val createObj = Program(
        title = "create object",
        description = "create object description",
        ownerId = UserId(1),
    )

    @Test
    fun createSuccess() = runTest {
        val result = repo.create(ProgramDbRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: ProgramId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertNotEquals(ProgramId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }
}
