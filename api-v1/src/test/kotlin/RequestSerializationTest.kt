package ru.artursitnikov.api.v1

import org.junit.jupiter.api.Test
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.api.v1.apiV1Mapper
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = CoachCreateRequest(
        requestId = "123",
        debug = CoachDebug(
            mode = CoachRequestDebugMode.STUB,
            stub = CoachRequestDebugStubs.BAD_EMAIL
        ),
        coach = CoachCreateObject(
            email = "test@test.ru",
            password = "1234",
            name = "some name",
            description = "description",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"name\":\\s*\"some name\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub"))
        assertContains(json, Regex("\"stub\":\\s*\"badEmail\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as CoachCreateRequest

        assertEquals(request, obj)
    }
}