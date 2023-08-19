package playground.booookstore.query.port

import kotlinx.datetime.LocalDateTime
import playground.booookstore.query.type.OrderId
import playground.booookstore.query.type.ShopId

interface OrderQueryPort {

    suspend fun find(orderId: OrderId): OrderQueryModel

}

data class OrderQueryModel(val id: OrderId, val orderDateTime: LocalDateTime, val shopId: ShopId)