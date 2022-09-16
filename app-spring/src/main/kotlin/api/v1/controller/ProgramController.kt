package ru.artursitnikov.fitness.appspring.api.v1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.mappers.v1.*
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.stubs.ProgramStub

@RestController
@RequestMapping("/api/v1/program")
class ProgramController {

    @PostMapping("create")
    fun createProgram(@RequestBody request: ProgramCreateRequest): ProgramCreateResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programResponse = ProgramStub.get()
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readProgram(@RequestBody request: ProgramReadRequest): ProgramReadResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programResponse = ProgramStub.get()
        return context.toTransportRead()
    }

    @PostMapping("update")
    fun updateProgram(@RequestBody request: ProgramUpdateRequest): ProgramUpdateResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programResponse = ProgramStub.get()
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deleteProgram(@RequestBody request: ProgramDeleteRequest): ProgramDeleteResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programResponse = ProgramStub.get()
        return context.toTransportDelete()
    }

    @PostMapping("list")
    fun listProgram(@RequestBody request: ProgramListRequest): ProgramListResponse {
        val context = ProgramContext()
        context.fromTransport(request)
        context.programListResponse = ProgramStub.getList()
        return context.toTransportList()
    }
}