package playground.booookstore.query.gateway

import kotlinx.coroutines.delay
import playground.booookstore.query.type.OrderId

class ItemQueryGateway {

    suspend fun find(orderId: OrderId): ItemsQueryModel {
        delay(3000L)
        @Suppress("SpellCheckingInspection")
        return ItemsQueryModel(
            setOf(
                ItemQueryModel(id = "9345", name = "LEFFNDP"),
                ItemQueryModel(id = "9347", name = "LEFFNDU"),
                ItemQueryModel(id = "9356", name = "LEFFNDT"),
            )
        )
    }

}

data class ItemsQueryModel(val set: Set<ItemQueryModel>)

data class ItemQueryModel(val id: String, val name: String)
