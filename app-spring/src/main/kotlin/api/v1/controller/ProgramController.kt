package ru.artursitnikov.fitness.appspring.api.v1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.biz.ProgramProcessor
import ru.artursitnikov.fitness.mappers.v1.*
import ru.artursitnikov.fitness.common.models.ProgramCommand

@RestController
@RequestMapping("/api/v1/program")
class ProgramController(
    private val processor: ProgramProcessor
) {
    @PostMapping("create")
    suspend fun createProgram(@RequestBody request: ProgramCreateRequest): ProgramCreateResponse =
        processV1(processor, ProgramCommand.CREATE, request)

    @PostMapping("read")
    suspend fun readProgram(@RequestBody request: ProgramReadRequest): ProgramReadResponse =
        processV1(processor, ProgramCommand.READ, request)

    @PostMapping("update")
    suspend fun updateProgram(@RequestBody request: ProgramUpdateRequest): ProgramUpdateResponse =
        processV1(processor, ProgramCommand.UPDATE, request)

    @PostMapping("delete")
    suspend fun deleteProgram(@RequestBody request: ProgramDeleteRequest): ProgramDeleteResponse =
        processV1(processor, ProgramCommand.DELETE, request)

    @PostMapping("list")
    suspend fun listProgram(@RequestBody request: ProgramListRequest): ProgramListResponse =
        processV1(processor, ProgramCommand.LIST, request)
}