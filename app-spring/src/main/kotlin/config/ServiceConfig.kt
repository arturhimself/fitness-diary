package ru.artursitnikov.fitness.appspring.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import ru.artursitnikov.fitness.biz.ProgramProcessor
import ru.artursitnikov.fitness.common.models.Settings
import ru.artursitnikov.fitness.repo.inmemory.ProgramRepoInmemory
import ru.artursitnikov.fitness.repo.postgres.DbConfig
import ru.artursitnikov.fitness.repo.postgres.ProgramRepoSql

@Configuration
class ServiceConfig {
    @Autowired
    lateinit var env: Environment

    @Bean
    fun programProcessor(): ProgramProcessor {
        return ProgramProcessor(
            settings = Settings(
                repoTest = ProgramRepoInmemory(),
                repoProd = ProgramRepoSql(config = DbConfig(
                    url = env.getProperty("database.url")!!,
                    user = env.getProperty("database.user")!!,
                    password = env.getProperty("database.password")!!,
                    schema = env.getProperty("database.schema")!!,
                ))
            ))
    }
}