import org.junit.jupiter.api.Test
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.common.CoachContext
import ru.artursitnikov.fitness.common.models.*
import ru.artursitnikov.fitness.common.stubs.CoachStubs
import kotlin.test.assertEquals

class MappersTest {
    @Test
    fun fromTransport() {
        val request = CoachCreateRequest(
            requestId = "123",
            debug = CoachDebug(
                mode = CoachRequestDebugMode.STUB,
                stub = CoachRequestDebugStubs.BAD_NAME
            ),
            coach = CoachCreateObject(
                email = "test@test.com",
                password = "1234",
                name = "Test",
                description = ""
            )
        )

        val context = CoachContext()
        context.fromTransport(request)

        assertEquals(RequestId("123"), context.requestId)
        assertEquals(CoachStubs.BAD_NAME, context.stubCase)
        assertEquals("test@test.com", context.coachRequest.email)
    }

    @Test
    fun toTransport() {
        val context = CoachContext(
            requestId = RequestId("123"),
            state = ContextState.RUNNING,
            command = CoachCommand.CREATE,
            coachResponse = Coach(
                email = "test@test.ru",
                name = "John"
            ),
            errors = mutableListOf(
                GeneralError(
                    code = "validation error",
                    group = "validation",
                    field = "password",
                    message = "not valid password",
                )
            )
        )

        val request = context.toTransport()

        assertEquals("123", request.requestId)
        assertEquals(ResponseResult.SUCCESS, request.result)
        assertEquals("validation error", request.errors?.firstOrNull()?.code)
        assertEquals("password", request.errors?.firstOrNull()?.field)
    }
}