package booookstore.playground

import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AsyncTest {

    @Test
    fun async() = runBlocking {
        val deferredA = async {
            delay(100L)
            "hello"
        }
        val deferredB = async {
            delay(100L)
            "world"
        }
        listOf(deferredA, deferredB).joinAll()
        assertEquals("hello", deferredA.getCompleted())
        assertEquals("world", deferredB.getCompleted())
    }

}