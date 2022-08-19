import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class HelloTest {

    @Test
    fun `should return Hello!`() {
        assertEquals("Hello!", getHello())
    }

    @ParameterizedTest(name = "{index}: argument {0}")
    @MethodSource("hellos")
    fun `should return Hello, Username!`(name: String, expected: String) {
        assertEquals(expected, getHello(name))
    }

    companion object {
        @JvmStatic
        fun hellos() = listOf(
            Arguments.of("Artur", "Hello, Artur!"),
            Arguments.of("username", "Hello, username!"),
        )
    }
}