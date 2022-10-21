package ru.artursitnikov.fitness.kafka

import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.artursitnikov.fitness.api.v1.apiV1ResponseDeserialize
import ru.artursitnikov.fitness.api.v1.apiV1ResponseSerialize
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.biz.ProgramProcessor
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.kafka.common.TOPIC_OUT
import ru.artursitnikov.fitness.mappers.v1.fromTransport
import ru.artursitnikov.fitness.mappers.v1.toTransport
import kotlin.test.assertEquals

class KafkaControllerTest {
    private val processor = ProgramProcessor()

    @Test
    fun producerTest() = runBlocking {
        val producer = MockProducer(true, StringSerializer(), StringSerializer())
        val context = ProgramContext()

        context.fromTransport(ProgramReadRequest(
            requestId = "11111111-1111-1111-1111-111111111111",
            program = ProgramReadObject(
                id = 1
            ),
            debug = ProgramDebug(
                mode = ProgramRequestDebugMode.STUB,
                stub = ProgramRequestDebugStubs.SUCCESS
            )
        ))
        processor.exec(context)

        producer.send(ProducerRecord(
            TOPIC_OUT,
            PARTITION,
            "test-1",
            apiV1ResponseSerialize(context.toTransport())
        ))

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<ProgramReadResponse>(message.value())
        assertEquals(TOPIC_OUT, message.topic())
        assertEquals("11111111-1111-1111-1111-111111111111", result.requestId)
        assertEquals("Muscle growth", result.program?.title)
    }

    companion object {
        const val PARTITION = 0
    }
}
