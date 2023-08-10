package playground.booookstore.query.repository

import playground.booookstore.query.type.ItemId
import playground.booookstore.query.type.OrderId

interface ItemQueryRepository {

    suspend fun find(orderId: OrderId): ItemsQueryModel

}

data class ItemsQueryModel(val set: Set<ItemQueryModel>)

data class ItemQueryModel(val id: ItemId, val name: String)
