package ru.artursitnikov.fitness.appspring.api.v1.controller

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.assertj.core.api.Assertions.assertThat
import ru.artursitnikov.fitness.api.v1.models.*

@WebFluxTest(ProgramController::class)
internal class ProgramControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockBean
    private lateinit var programController: ProgramController

    @Test
    fun createProgram(): Unit = runBlocking {
        val programCreateResponse = ProgramCreateResponse(
            responseType = "create",
            result = ResponseResult.SUCCESS,
            program = ProgramResponseObject()
        )

        Mockito.`when`(programController.createProgram(any())).thenReturn(programCreateResponse)

        webClient.post()
            .uri("/api/v1/program/create")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(ProgramCreateRequest()))
            .exchange()
            .expectStatus().isOk
            .expectBody(ProgramCreateResponse::class.java)
            .value {
                println(it)
                assertThat(it.responseType).isEqualTo("create")
            }
    }
}