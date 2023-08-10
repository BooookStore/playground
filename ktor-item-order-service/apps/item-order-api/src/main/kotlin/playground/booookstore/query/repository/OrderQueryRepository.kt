package playground.booookstore.query.repository

import playground.booookstore.query.type.OrderId
import java.time.LocalDateTime

interface OrderQueryRepository {

    suspend fun findOrder(orderId: OrderId): OrderQueryModel

}

data class OrderQueryModel(val id: OrderId, val orderDateTime: LocalDateTime)
