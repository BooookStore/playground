package playground.booookstore.query.gateway

import kotlinx.coroutines.delay
import playground.booookstore.query.driver.HttpClientShopDriver
import playground.booookstore.query.port.ShopQueryModel
import playground.booookstore.query.port.ShopQueryPort
import playground.booookstore.query.type.ShopId

class ShopQueryGateway(private val httpClientShopDriver: HttpClientShopDriver) : ShopQueryPort {

    override suspend fun find(shopId: ShopId): ShopQueryModel {
        delay(1000L)
        httpClientShopDriver.findShopById(shopId)
        return ShopQueryModel("SA0078", "さいたま川越一号店")
    }

}
