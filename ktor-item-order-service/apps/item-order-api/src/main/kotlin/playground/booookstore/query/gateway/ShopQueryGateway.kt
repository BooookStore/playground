package playground.booookstore.query.gateway

import kotlinx.coroutines.delay
import playground.booookstore.query.port.ShopQueryModel
import playground.booookstore.query.port.ShopQueryPort
import playground.booookstore.query.type.ShopId

class ShopQueryGateway : ShopQueryPort {

    override suspend fun find(shopId: ShopId): ShopQueryModel {
        delay(1000L)
        return ShopQueryModel("SA0078", "さいたま川越一号店")
    }

}
