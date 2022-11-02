package ru.artursitnikov.fitness.repo.postgres

import com.benasher44.uuid.uuid4
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.common.repo.*

class ProgramRepoSql(
    config: DbConfig,
    initObjects: Collection<Program> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : ProgramRepository {
    override suspend fun create(request: ProgramDbRequest): ProgramDbResponse {
        TODO("Not yet implemented")
    }

    override suspend fun read(): ProgramListDbResponse {
        TODO("Not yet implemented")
    }

    override suspend fun read(request: ProgramDbIdRequest): ProgramDbResponse {
        TODO("Not yet implemented")
    }

    override suspend fun update(request: ProgramDbRequest): ProgramDbResponse {
        TODO("Not yet implemented")
    }

    override suspend fun delete(request: ProgramDbIdRequest): ProgramDbResponse {
        TODO("Not yet implemented")
    }
}