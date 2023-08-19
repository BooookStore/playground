package playground.booookstore.query.port

import playground.booookstore.query.type.OrderId
import playground.booookstore.query.type.ShopId

interface ShopQueryPort {

    suspend fun find(orderId: OrderId): ShopQueryModel

}

data class ShopQueryModel(val id: ShopId, val name: String)