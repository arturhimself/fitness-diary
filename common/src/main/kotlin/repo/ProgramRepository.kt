package ru.artursitnikov.fitness.common.repo

interface ProgramRepository {
    suspend fun create(request: ProgramDbRequest): ProgramDbResponse
    suspend fun read(): ProgramListDbResponse
    suspend fun read(request: ProgramDbIdRequest): ProgramDbResponse
    suspend fun update(request: ProgramDbRequest): ProgramDbResponse
    suspend fun delete(request: ProgramDbIdRequest): ProgramDbResponse

    companion object {
        val NONE = object : ProgramRepository {
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
    }
}