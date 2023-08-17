package playground.booookstore.query.gateway

import kotlinx.coroutines.delay
import playground.booookstore.query.type.OrderId
import playground.booookstore.query.type.ShopId

class ShopQueryGateway {
    suspend fun find(orderId: OrderId): ShopQueryModel {
        delay(1000L)
        return ShopQueryModel("SA0078", "さいたま川越一号店")
    }

}

data class ShopQueryModel(val id: ShopId, val name: String) {
}