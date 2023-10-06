package playground.booookstore.query.driver


import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import playground.booookstore.query.type.ShopId

class HttpClientShopDriver(private val httpClient: HttpClient) {

    suspend fun findShopById(shopId: ShopId): HttpShopResponse {
        return httpClient.get("http://localhost:8082/v1/shop/$shopId").body<HttpShopResponse>()
    }

    @Serializable
    data class HttpShopResponse(val shopId: String, val name: String)

}