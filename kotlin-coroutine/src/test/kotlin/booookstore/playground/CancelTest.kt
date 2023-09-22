package booookstore.playground

import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CancelTest {

    @Test
    fun coroutineCancelable() {
        runBlocking {
            var count = 0
            val job = launch {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i")
                    count++
                    delay(500L)
                }
            }
            delay(1300L)
            println("main: I'm tired of waiting!")
            job.cancel()
            job.join()
            println("main: Now I can quit")
            assertEquals(3, count)
        }
    }

    @Test
    fun coroutineCancellationIsCooperative() {
        runBlocking {
            var counter = 0
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (i < 5) { // computation loop, just wastes CPU
                    // print a message twice a second
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("job: I'm sleeping ${i++}")
                        nextPrintTime += 500L
                        counter++
                    }
                }
            }
            delay(1300L)
            println("main: I'm tired of waiting!")
            job.cancelAndJoin()
            println("main: Now I can quit.")
            assertEquals(5, counter)
        }
    }

    @Test
    fun catchingCancellationException() {
        runBlocking {
            var counter = 0
            val job = launch(Dispatchers.Default) {
                repeat(5) { i ->
                    try {
                        // print message twice a second
                        println("job: I'm sleeping $i")
                        counter++
                        delay(500L)
                    } catch (e: Exception) {
                        // log the exception
                        println(e)
                    }
                }
            }
            delay(1300L)
            println("main: I'm tired of waiting!")
            job.cancelAndJoin()
            println("main: Now I can quit.")
            assertEquals(5, counter)
        }
    }

    @Test
    fun makingComputationCodeCancellable() {
        runBlocking {
            var counter = 0
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (isActive) { // computation loop, just wastes CPU
                    // print a message twice a second
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("job: I'm sleeping ${i++}")
                        nextPrintTime += 500L
                        counter++
                    }
                }
            }
            delay(1300L)
            println("main: I'm tired of waiting!")
            job.cancelAndJoin()
            println("main: Now I can quit.")
            assertEquals(3, counter)
        }
    }

    @Test
    fun runNonCancellableBlock() {
        runBlocking {
            var counter = 0
            val job = launch(Dispatchers.Default) {
                repeat(1000) { i ->
                    try {
                        // print message twice a second
                        println("job: I'm sleeping $i")
                        counter++
                        delay(500L)
                    } finally {
                        withContext(NonCancellable) {
                            println("job: I'm running finally")
                            delay(1000L)
                            println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                        }
                    }
                }
            }
            delay(1300L)
            println("main: I'm tired of waiting!")
            job.cancelAndJoin()
            println("main: Now I can quit.")
            assertEquals(2, counter)
        }
    }

    @Test
    fun timeout() {
        var counter = 0
        assertThrows<TimeoutCancellationException> {
            runBlocking {
                withTimeout(1300L) {
                    repeat(1000) { i ->
                        println("I'm sleeping $i ...")
                        counter++
                        delay(500L)
                    }
                }
            }
        }
        assertEquals(3, counter)
    }

}