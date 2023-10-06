package playground.booookstore.query.gateway

import playground.booookstore.query.driver.HttpClientShopDriver
import playground.booookstore.query.port.ShopQueryModel
import playground.booookstore.query.port.ShopQueryPort
import playground.booookstore.query.type.ShopId

class ShopQueryGateway(private val httpClientShopDriver: HttpClientShopDriver) : ShopQueryPort {

    override suspend fun find(shopId: ShopId): ShopQueryModel = httpClientShopDriver.findShopById(shopId).let {
        ShopQueryModel(it.shopId, it.name)
    }

}
