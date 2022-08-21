package ru.artursitnikov.api.v1

import org.junit.jupiter.api.Test
import ru.artursitnikov.fitness.api.v1.models.CoachCreateResponse
import ru.artursitnikov.fitness.api.v1.models.CoachResponseObject
import ru.artursitnikov.fitness.api.v1.models.IResponse
import ru.artursitnikov.fitness.api.v1.models.ResponseResult
import ru.artursitnikov.fitness.api.v1.apiV1Mapper
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = CoachCreateResponse(
        requestId = "123",
        result = ResponseResult.SUCCESS,
        coach = CoachResponseObject(
            email = "test@test.ru",
            password = "1234",
            name = "John",
            description = ""
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"email\":\\s*\"test@test.ru\""))
        assertContains(json, Regex("\"description\":\\s*\"\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as CoachCreateResponse

        assertEquals(response, obj)
    }
}