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