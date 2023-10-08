package playground.booookstore

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class HelloTest {

    lateinit var httpClient: HttpClient

    @Before
    fun setupKtorHttpClient() {
        httpClient = HttpClient(CIO)
    }

    @Test
    fun hello() {
        assertEquals(1, 1)
    }

}
