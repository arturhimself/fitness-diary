import com.benasher44.uuid.uuid4
import org.testcontainers.containers.PostgreSQLContainer
import ru.artursitnikov.fitness.common.models.Program
import ru.artursitnikov.fitness.repo.postgres.DbConfig
import ru.artursitnikov.fitness.repo.postgres.ProgramRepoSql
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "postgres"
    private const val SCHEMA = "public"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        initObjects: Collection<Program> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): ProgramRepoSql {
        val config = DbConfig(
            url = url,
            user = USER,
            password = PASS,
            schema = SCHEMA
        )
        return ProgramRepoSql(config, initObjects, randomUuid)
    }
}