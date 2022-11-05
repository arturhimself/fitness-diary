package ru.artursitnikov.fitness.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.artursitnikov.fitness.common.helpers.errorRepoConcurrency
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.models.ProgramId
import ru.artursitnikov.fitness.common.models.ProgramLock
import ru.artursitnikov.fitness.common.repo.*
import ru.artursitnikov.fitness.repo.inmemory.models.ProgramEntity

/**
 * In memory repository.
 * Use for tests or prototypes only
 */
class ProgramRepoInmemory(
    initObjects: List<Program> = emptyList(),
    private val randomUuid: () -> String = { uuid4().toString() }
) : ProgramRepository {
    private val cache = Cache.Builder().build<Long, ProgramEntity>()

    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(program: Program) {
        val key = program.id.takeIf { it != ProgramId.NONE }?.asLong() ?: return
        cache.put(key, ProgramEntity(program))
    }

    override suspend fun create(request: ProgramDbRequest): ProgramDbResponse {
        val key = (0..1000).random().toLong()
        val entity = ProgramEntity(request.program.copy().apply {
            id = ProgramId(key)
            lock = ProgramLock(randomUuid())
        })
        cache.put(key, entity)
        return ProgramDbResponse(
            data = entity.toInternal(),
            isSuccess = true,
        )
    }

    override suspend fun read(): ProgramListDbResponse {
        val programs = cache.asMap().map { it.value.toInternal() }
        return ProgramListDbResponse(
            data = programs,
            isSuccess = true
        )
    }

    override suspend fun read(request: ProgramDbIdRequest): ProgramDbResponse {
        val key = request.id.takeIf { it != ProgramId.NONE }?.asLong() ?: return resultErrorEmptyId
        return cache.get(key)?.let { ProgramDbResponse(
            data = it.toInternal(),
            isSuccess = true
        ) } ?: resultErrorNotFound(key.toString())
    }

    override suspend fun update(request: ProgramDbRequest): ProgramDbResponse {
        val key = request.program.id.takeIf { it != ProgramId.NONE }?.asLong() ?: return resultErrorEmptyId
        val newLock = randomUuid()
        val oldLock = request.program.lock.takeIf { it != ProgramLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val program = ProgramEntity(request.program.copy().apply { lock = ProgramLock(newLock) })
        return mutex.withLock {
            val oldProgram = cache.get(key)
            when {
                oldProgram == null -> return resultErrorNotFound(key.toString())
                oldProgram.lock != oldLock -> ProgramDbResponse(
                    data = oldProgram.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(ProgramLock(oldLock), oldProgram.lock?.let { ProgramLock(it) }))
                )
                else -> {
                    cache.put(key, program)
                    ProgramDbResponse(
                        data = program.toInternal(),
                        isSuccess = true
                    )
                }
            }
        }
    }

    override suspend fun delete(request: ProgramDbIdRequest): ProgramDbResponse {
        val key = request.id.takeIf { it != ProgramId.NONE }?.asLong() ?: return resultErrorEmptyId
        val oldLock = request.lock.takeIf { it != ProgramLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldProgram = cache.get(key)
            when {
                oldProgram == null -> return resultErrorNotFound(key.toString())
                oldProgram.lock != oldLock -> ProgramDbResponse(
                    data = oldProgram.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(ProgramLock(oldLock), oldProgram.lock?.let { ProgramLock(it) }))
                )
                else -> {
                    cache.invalidate(key)
                    ProgramDbResponse(
                        data = oldProgram.toInternal(),
                        isSuccess = true
                    )
                }
            }
        }
    }
}