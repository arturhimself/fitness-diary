package ru.artursitnikov.fitness.appspring.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@Configuration
@ComponentScan("ru.artursitnikov.fitness.kafka")
class KafkaConfig