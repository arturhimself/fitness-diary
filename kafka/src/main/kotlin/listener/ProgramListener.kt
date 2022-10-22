package ru.artursitnikov.fitness.kafka.listener

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import ru.artursitnikov.fitness.api.v1.apiV1RequestDeserialize
import ru.artursitnikov.fitness.api.v1.models.IRequest
import ru.artursitnikov.fitness.api.v1.models.IResponse
import ru.artursitnikov.fitness.biz.ProgramProcessor
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.kafka.common.TOPIC_IN
import ru.artursitnikov.fitness.kafka.common.TOPIC_OUT
import ru.artursitnikov.fitness.mappers.v1.fromTransport
import ru.artursitnikov.fitness.mappers.v1.toTransport

@Component
class ProgramListener(private val kafkaTemplate: KafkaTemplate<String, IResponse>) {
    private val processor = ProgramProcessor()

    private val log = KotlinLogging.logger {}

    @KafkaListener(topics = [TOPIC_IN], autoStartup = "true")
    fun handleEvent(request: String) = runBlocking {
        log.info("Received Message in topic $TOPIC_IN. Message: $request")

        val context = ProgramContext().apply { timeStart = Clock.System.now() }
        val requestDeserialized: IRequest = apiV1RequestDeserialize(request)

        context.fromTransport(requestDeserialized)
        processor.exec(context)
        kafkaTemplate.send(TOPIC_OUT, context.toTransport()).addCallback(
            { log.info("Event sent to topic $TOPIC_OUT") },
            { log.info("Event not sent to topic $TOPIC_OUT, cause ${it.cause}") }
        )
    }
}