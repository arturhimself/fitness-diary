package ru.artursitnikov.api.v1

import org.junit.jupiter.api.Test
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.api.v1.apiV1Mapper
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = ProgramCreateRequest(
        requestId = "123",
        debug = ProgramDebug(
            mode = ProgramRequestDebugMode.STUB,
            stub = ProgramRequestDebugStubs.BAD_TITLE
        ),
        program = ProgramCreateObject(
            title = "program title",
            description = "description",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"program title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub"))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as ProgramCreateRequest

        assertEquals(request, obj)
    }
}