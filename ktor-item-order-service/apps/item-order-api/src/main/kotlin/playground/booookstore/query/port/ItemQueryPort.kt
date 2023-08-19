package playground.booookstore.query.port

import playground.booookstore.query.type.OrderId

interface ItemQueryPort {

    suspend fun find(orderId: OrderId): ItemsQueryModel

}

data class ItemsQueryModel(val set: Set<ItemQueryModel>)

data class ItemQueryModel(val id: String, val name: String)