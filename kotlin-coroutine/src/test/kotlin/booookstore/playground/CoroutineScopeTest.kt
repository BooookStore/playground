package booookstore.playground

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CoroutineScopeTest {

    @Test
    fun coroutineScope() = runBlocking {
        val message = otherCoroutineScope()
        println("receive message from otherCoroutineScope")
        assertEquals("hello", message)
    }

    private suspend fun otherCoroutineScope(): String = coroutineScope {
        println("entry otherCoroutineScope")
        val deferred = async {
            delay(100L)
            "hello"
        }
        deferred.await()
    }

}