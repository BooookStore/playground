package playground.booookstore

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class HelloTest {

    val httpClient = HttpClient(CIO) {
        engine {
            maxConnectionsCount = 3
        }
    }

    lateinit var server: WireMockServer

    @Before
    fun startWiremockServer() {
        server = WireMockServer(WireMockConfiguration.options().dynamicPort().notifier(Slf4jNotifier(true))).apply {
            start()
            stubFor(
                get(anyUrl()).willReturn(aResponse().withStatus(200))
            )
        }
    }

    @After
    fun shutdownWiremockServer() {
        server.shutdownServer()
    }

    @Test
    fun hello() {
        runBlocking {
            httpClient.get("http://localhost:${server.port()}/v1/ping")
        }
    }

}
