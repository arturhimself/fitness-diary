package ru.artursitnikov.fitness.appspring.api.v1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.mappers.v1.*
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.Program

@RestController("/api/v1/program")
class ProgramController {

    @PostMapping("create")
    fun createProgram(@RequestBody request: ProgramCreateRequest): ProgramCreateResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programResponse = Program()
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readProgram(@RequestBody request: ProgramReadRequest): ProgramReadResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programResponse = Program()
        return context.toTransportRead()
    }

    @PostMapping("update")
    fun readProgram(@RequestBody request: ProgramUpdateRequest): ProgramUpdateResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programResponse = Program()
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun readProgram(@RequestBody request: ProgramDeleteRequest): ProgramDeleteResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programResponse = Program()
        return context.toTransportDelete()
    }

    @PostMapping("list")
    fun readProgram(@RequestBody request: ProgramListRequest): ProgramListResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programListResponse = mutableListOf(Program())
        return context.toTransportList()
    }
}