package playground.booookstore.query.gateway

import kotlinx.datetime.toKotlinLocalDateTime
import playground.booookstore.query.driver.RDBOrderDriver
import playground.booookstore.query.port.OrderQueryModel
import playground.booookstore.query.port.OrderQueryPort
import playground.booookstore.query.type.OrderId

class OrderQueryGateway(private val rdbOrderDriver: RDBOrderDriver) : OrderQueryPort {

    override suspend fun find(orderId: OrderId): OrderQueryModel = rdbOrderDriver.find(orderId)?.let {
        OrderQueryModel(id = it.id, orderDateTime = it.orderDateTime.toKotlinLocalDateTime(), shopId = it.shopId)
    } ?: throw Exception("order not found. search by orderId[$orderId]")

}
