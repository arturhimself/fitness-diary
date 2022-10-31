package ru.artursitnikov.fitness.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.repo.ProgramDbIdRequest
import ru.artursitnikov.fitness.common.repo.ProgramRepository
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoReadProgramTest {
    abstract val repo: ProgramRepository

    @Test
    fun readSuccess() = runTest {
        val result = repo.read(ProgramDbIdRequest(successId))
        assertEquals(true, result.isSuccess)
        assertEquals(readSuccessStub, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readAllSuccess() = runTest {
        val result = repo.read()
        assertEquals(true, result.isSuccess)
        assertEquals(initObjects.size, result.data?.size)
    }

    @Test
    fun readNotFound() = runTest {
        val result = repo.read(ProgramDbIdRequest(notFoundId))
        val error = result.errors.find { it.code == "not-found" }
        assertEquals(null, result.data)
        assertEquals(false, result.isSuccess)
        assertEquals("id", error?.field)
    }

    companion object : BaseInitAds() {
        override val initObjects: List<Program> = listOf(
            createInitTestModel(id = ProgramId(1), suf = "read-1"),
            createInitTestModel(id = ProgramId(2), suf = "read-2"),
        )
        private val readSuccessStub = initObjects.first()

        val successId = ProgramId(readSuccessStub.id.asLong())
        val notFoundId = ProgramId(49)
    }
}
