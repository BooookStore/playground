package playground.booookstore.query.gateway

import kotlinx.datetime.LocalDateTime
import playground.booookstore.query.driver.RdbOrderDriver
import playground.booookstore.query.port.OrderQueryModel
import playground.booookstore.query.port.OrderQueryPort
import playground.booookstore.query.type.OrderId

class OrderQueryGateway(private val rdbOrderDriver: RdbOrderDriver) : OrderQueryPort {

    override suspend fun find(orderId: OrderId): OrderQueryModel =
        OrderQueryModel(id = orderId, orderDateTime = LocalDateTime.parse("2023-08-18T14:52:00.00"), shopId = "9870")

}
