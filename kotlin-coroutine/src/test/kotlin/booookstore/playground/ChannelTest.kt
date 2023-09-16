package booookstore.playground

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ChannelTest {

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
    fun channelTest() = runBlocking {
        val channelA = Channel<Int>(3)
        launch { produce(channelA) }
        val result = (1..5).map { async { consume(channelA) } }.toList().awaitAll().flatten().sorted()
        assertEquals((1..100).toList(), result)
    }

    private suspend fun produce(channelA: Channel<Int>) = coroutineScope {
        val sendChannel: SendChannel<Int> = channelA
        (1..100).forEach {
            println("produce $it")
            sendChannel.send(it)
        }
        sendChannel.close()
    }

    private suspend fun consume(receiveChannel: ReceiveChannel<Int>): List<Int> = coroutineScope {
        receiveChannel.consumeAsFlow().map {
            println("${Thread.currentThread().name} consume $it")
            it
        }.toList()
    }

}