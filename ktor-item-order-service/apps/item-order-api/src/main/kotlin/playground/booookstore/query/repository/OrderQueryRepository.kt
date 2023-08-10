package playground.booookstore.query.repository

import kotlinx.datetime.LocalDateTime
import playground.booookstore.query.type.OrderId

interface OrderQueryRepository {

    suspend fun findOrder(orderId: OrderId): OrderQueryModel

}

data class OrderQueryModel(val id: OrderId, val orderDateTime: LocalDateTime)
