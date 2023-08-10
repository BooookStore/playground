package playground.booookstore.query.gateway

import playground.booookstore.query.repository.ItemQueryRepository
import playground.booookstore.query.repository.ItemsQueryModel
import playground.booookstore.query.type.OrderId

class ItemQueryGateway : ItemQueryRepository {

    override suspend fun find(orderId: OrderId): ItemsQueryModel {
        TODO("Not yet implemented")
    }

}