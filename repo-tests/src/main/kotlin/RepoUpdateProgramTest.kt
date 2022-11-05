package ru.artursitnikov.fitness.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.models.ProgramLock
import ru.artursitnikov.fitness.common.repo.ProgramDbRequest
import ru.artursitnikov.fitness.common.repo.ProgramRepository
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoUpdateProgramTest {
    abstract val repo: ProgramRepository

    protected open val lockNew: ProgramLock = ProgramLock("20000000-0000-0000-0000-000000000002")

    @Test
    fun updateSuccess() = runTest {
        val result = repo.update(ProgramDbRequest(updatedProgram))
        assertEquals(true, result.isSuccess)
        assertEquals(updatedProgram.title, result.data?.title)
        assertEquals(updatedProgram.description, result.data?.description)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runTest {
        val result = repo.update(ProgramDbRequest(updatedProgramNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrency() = runTest {
        val result = repo.update(ProgramDbRequest(updatedProgramWithBadLock))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
    }

    companion object : BaseInitAds() {
        override val initObjects: List<Program> = listOf(createInitTestModel("update"))

        val updatedProgram = initObjects.first().copy().apply {
            title = "updated title"
            description = "updated description"
        }
        val updatedProgramNotFound = updatedProgram.copy().apply { id = ProgramId(9) }
        val updatedProgramWithBadLock = updatedProgram.copy().apply { lock = lockBad }
    }
}
