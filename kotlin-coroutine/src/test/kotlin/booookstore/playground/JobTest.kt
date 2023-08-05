package booookstore.playground

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JobTest {

    @Test
    fun job() = runBlocking {
        var message = ""
        val job = launch {
            delay(100L)
            message += "hello"
        }
        job.join()
        assertEquals("hello", message)
    }

}