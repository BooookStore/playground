package booookstore.playground

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SuspendFunctionTest {

    @Test
    fun suspendFunctionTest() = runBlocking {
        val message = message1()
        assertEquals("hello world", message)
    }

    private suspend fun message1(): String {
        delay(100L)
        val message = message2()
        return "hello$message"
    }

    private suspend fun message2(): String {
        delay(100L)
        return " world"
    }

}