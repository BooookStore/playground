package booookstore.playground

import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CoroutineScopeTest {

    @Test
    fun coroutineScope() = runBlocking {
        val message = otherCoroutineScope()
        assertEquals("hello", message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun otherCoroutineScope(): String = coroutineScope {
        val deferred = async {
            delay(100L)
            "hello"
        }
        deferred.await()
    }

}