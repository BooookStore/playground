package booookstore.playground

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JobTest {

    @Test
    fun job() = runBlocking {
        var message = ""
        val jobA = launch {
            delay(100L)
            message += "hello"
        }
        val jobB = launch {
            delay(200L)
            message += ",world"
        }
        listOf(jobA, jobB).joinAll()
        assertEquals("hello,world", message)
    }
}