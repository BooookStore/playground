package booookstore.playground

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.testTimeSource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class ComposingSuspendingFunctions {

    private suspend fun doSomethingUsefulOne(): Int {
        delay(100L)
        return 13
    }

    private suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L)
        return 29
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
    @Test
    fun sequential() = runTest {
        val time = testTimeSource.measureTime {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("${one + two}")
        }
        assertEquals(Duration.parse("1.1s"), time)
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
    @Test
    fun concurrentUsingAsync() = runTest {
        val time = testTimeSource.measureTime {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("${one.await() + two.await()}")
        }
        assertEquals(Duration.parse("1s"), time)
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
    @Test
    fun lazilyStartedAsync() = runTest {
        val time = testTimeSource.measureTime {
            val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
            one.start()
            two.start()
            println("${one.await() + two.await()}")
        }
        assertEquals(Duration.parse("1s"), time)
    }
}