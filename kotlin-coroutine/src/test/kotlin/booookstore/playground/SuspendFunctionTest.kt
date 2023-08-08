package booookstore.playground

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SuspendFunctionTest {

    @Test
    fun suspendFunctionTest() = runBlocking {
        val message = message()
        assertEquals("hello", message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun message(): String {
        delay(100L)
        return "hello"
    }

}