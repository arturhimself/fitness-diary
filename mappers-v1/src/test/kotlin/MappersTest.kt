import org.junit.jupiter.api.Test
import ru.artursitnikov.fitness.api.v1.models.*
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.*
import ru.artursitnikov.fitness.common.stubs.ProgramStubs
import kotlin.test.assertEquals

class MappersTest {
    @Test
    fun fromTransport() {
        val request = ProgramCreateRequest(
            requestId = "123",
            debug = ProgramDebug(
                mode = ProgramRequestDebugMode.STUB,
                stub = ProgramRequestDebugStubs.BAD_TITLE
            ),
            program = ProgramCreateObject(
                title = "",
                description = ""
            )
        )

        val context = ProgramContext()
        context.fromTransport(request)

        assertEquals(RequestId("123"), context.requestId)
        assertEquals(ProgramStubs.BAD_TITLE, context.stubCase)
        assertEquals("", context.programRequest.title)
    }

    @Test
    fun toTransport() {
        val context = ProgramContext(
            requestId = RequestId("123"),
            state = ContextState.RUNNING,
            command = ProgramCommand.CREATE,
            programResponse = Program(
                title = "",
                description = "foo"
            ),
            errors = mutableListOf(
                GeneralError(
                    code = "validation.error",
                    group = "validation",
                    field = "title",
                    message = "not valid title",
                )
            )
        )

        val request = context.toTransport()

        assertEquals("123", request.requestId)
        assertEquals(ResponseResult.SUCCESS, request.result)
        assertEquals("validation.error", request.errors?.firstOrNull()?.code)
        assertEquals("title", request.errors?.firstOrNull()?.field)
    }
}