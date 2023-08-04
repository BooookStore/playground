package booookstore.playground

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HelloCoroutineTest {

    @Test
    fun sayHello() {
        var message = ""
        runBlocking {
            launch {
                delay(100L)
                message += "World. "
            }
            message += "Hello, "
        }
        message += "from coroutine."

        assertEquals("Hello, World. from coroutine.", message)
    }

}
