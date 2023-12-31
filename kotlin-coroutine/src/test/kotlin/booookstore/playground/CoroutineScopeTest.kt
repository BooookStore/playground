package booookstore.playground

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@Suppress("NonAsciiCharacters")
class CoroutineScopeTest {

    @Test
    fun coroutineScope() = runBlocking {
        val message = secondCoroutineScope()
        assertEquals("hello", message)
    }

    private suspend fun secondCoroutineScope(): String = coroutineScope {
        async {
            delay(100L)
            "hello"
        }.await()
    }

    @Test
    fun `CoroutineScopeは新たなCoroutineを作成するのではなく、現在のCoroutineを引き継ぐ`() = runBlocking {
        val message = otherCoroutineScope()
        println("${Thread.currentThread().name}: > receive message from otherCoroutineScope")
        assertEquals("hello", message)
    }

    private suspend fun otherCoroutineScope(): String = coroutineScope {
        println("${Thread.currentThread().name}: > entry otherCoroutineScope")
        val deferred = async {
            println("${Thread.currentThread().name}: >> entry async in otherCoroutineScope")
            delay(100L)
            coroutineScope {
                println("${Thread.currentThread().name}: >> entry coroutineScope in async in otherCoroutineScope")
                delay(100L)
            }
            "hello"
        }
        deferred.await()
    }

}