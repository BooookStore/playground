package playground.booookstore.query.gateway

import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import playground.booookstore.query.repository.OrderQueryModel
import playground.booookstore.query.repository.OrderQueryRepository
import playground.booookstore.query.type.OrderId

class OrderQueryGateway : OrderQueryRepository {

    override suspend fun find(orderId: OrderId): OrderQueryModel {
        delay(3000L)
        return OrderQueryModel(id = orderId, orderDateTime = LocalDateTime(2023, Month.AUGUST, 10, 21, 12))
    }

}
