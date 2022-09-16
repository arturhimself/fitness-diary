package ru.artursitnikov.fitness.appspring.api.v1.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.artursitnikov.fitness.api.v1.models.*

@WebMvcTest(ProgramController::class)
internal class ProgramControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var programController: ProgramController

    @Test
    fun createProgram() {
        val programCreateResponse = ProgramCreateResponse(
            responseType = "create",
            result = ResponseResult.SUCCESS,
            program = ProgramResponseObject()
        )

        val request = mapper.writeValueAsString(ProgramCreateRequest())
        val response = mapper.writeValueAsString(programCreateResponse)

        Mockito.`when`(programController.createProgram(any())).thenReturn(programCreateResponse)

        mockMvc
            .perform(
                post("/api/v1/program/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
            )
            .andExpect(status().isOk)
            .andExpect(content().json(response))
    }

    @Test
    fun readProgram() {
        val programReadResponse = ProgramReadResponse(
            responseType = "read",
            result = ResponseResult.SUCCESS,
            program = ProgramResponseObject()
        )

        val request = mapper.writeValueAsString(ProgramReadRequest())
        val response = mapper.writeValueAsString(programReadResponse)

        Mockito.`when`(programController.readProgram(any())).thenReturn(programReadResponse)

        mockMvc
            .perform(
                post("/api/v1/program/read")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
            )
            .andExpect(status().isOk)
            .andExpect(content().json(response))
    }
}