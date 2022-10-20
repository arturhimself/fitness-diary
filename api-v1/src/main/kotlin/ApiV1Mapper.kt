package ru.artursitnikov.fitness.api.v1

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import ru.artursitnikov.fitness.api.v1.models.IRequest

val apiV1Mapper = ObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiV1RequestDeserialize(json: String): T =
    apiV1Mapper.readValue(json, IRequest::class.java) as T
