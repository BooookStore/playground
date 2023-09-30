package playground.booookstore

import com.thoughtworks.gauge.Step
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals

class Steps {

    private val httpClient = HttpClient(CIO)

    private var httpResponse: HttpResponse? = null

    @Step("オーダーIDが<orderId>であるオーダーを取得する")
    fun getOrderByOrderID(orderId: String) = runBlocking {
        httpResponse = httpClient.get("http://localhost:8080/order/$orderId")
    }

    @Step("レスポンスステータスが<httpResponseStatusCode>である")
    fun assertHttpResponseStatusCode(httpResponseStatusCode: String) {
        assertEquals(200, httpResponse!!.status.value)
    }

    @Step("レスポンスのオーダーIDは<orderId>である")
    fun assertRespondOrderId(orderId: String) {
        TODO()
    }

}