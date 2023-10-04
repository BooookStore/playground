package playground.booookstore

import com.thoughtworks.gauge.Step
import io.github.nomisrev.JsonPath
import io.github.nomisrev.path
import io.github.nomisrev.string
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
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
        assertEquals(httpResponseStatusCode, httpResponse!!.status.value.toString())
    }

    @Step("レスポンスのJSONの<jsonPath>は<orderId>である")
    fun assertRespondOrderId(jsonPath: String, orderId: String) = runBlocking {
        val body = httpResponse!!.body<String>()
        val respondJsonElement: JsonElement = Json.decodeFromString<JsonElement>(body)
        val jsonPathOrderId = JsonPath.path(jsonPath).string
        assertEquals(orderId, jsonPathOrderId.getOrNull(respondJsonElement), "respond body is $body")
    }

}