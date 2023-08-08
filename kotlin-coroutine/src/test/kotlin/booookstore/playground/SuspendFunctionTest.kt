package booookstore.playground

import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SuspendFunctionTest {

    @Test
    fun suspendFunctionTest() = runBlocking {
        val message = message()
        println("1")
        assertEquals("hello", message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun message(): String = coroutineScope {
        val deferred = async {
            delay(100L)
            println("2")
            "hello"
        }
        deferred.join()
        deferred.getCompleted()
    }

}