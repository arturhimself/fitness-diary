package ru.artursitnikov.fitness.appspring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.artursitnikov.fitness.biz.ProgramProcessor

@Configuration
class ServiceConfig {
    @Bean
    fun programProcessor(): ProgramProcessor = ProgramProcessor()
}