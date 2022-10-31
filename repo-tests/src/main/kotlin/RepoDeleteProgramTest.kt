package ru.artursitnikov.fitness.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.models.ProgramLock
import ru.artursitnikov.fitness.common.repo.ProgramDbIdRequest
import ru.artursitnikov.fitness.common.repo.ProgramRepository
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDeleteProgramTest {
    abstract val repo: ProgramRepository

    protected open val lockNew: ProgramLock = ProgramLock("20000000-0000-0000-0000-000000000002")

    @Test
    fun deleteSuccess() = runTest {
        val result = repo.delete(ProgramDbIdRequest(successId, successLock))
        assertEquals(true, result.isSuccess, "success")
        assertEquals(emptyList(), result.errors, "errors")
        assertEquals(lockOld, result.data?.lock, "lock")
    }

    @Test
    fun deleteNotFound() = runTest {
        val result = repo.delete(ProgramDbIdRequest(ProgramId(9), successLock))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrency() = runTest {
        val result = repo.delete(ProgramDbIdRequest(successId, lockBad))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
    }

    companion object : BaseInitAds() {
        override val initObjects: List<Program> = listOf(createInitTestModel("delete"))
        val successId = initObjects.first().id
        val successLock = initObjects.first().lock
    }
}
