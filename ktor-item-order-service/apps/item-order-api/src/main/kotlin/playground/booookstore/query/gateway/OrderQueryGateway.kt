package playground.booookstore.query.gateway

import playground.booookstore.query.repository.OrderQueryModel
import playground.booookstore.query.repository.OrderQueryRepository
import playground.booookstore.query.type.OrderId

class OrderQueryGateway : OrderQueryRepository {
    override suspend fun findOrder(orderId: OrderId): OrderQueryModel {
        TODO("Not yet implemented")
    }

}
