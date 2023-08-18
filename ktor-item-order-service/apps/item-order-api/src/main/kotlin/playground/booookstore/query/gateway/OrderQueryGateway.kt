package playground.booookstore.query.gateway

import kotlinx.datetime.LocalDateTime
import playground.booookstore.query.driver.RdbOrderDriver
import playground.booookstore.query.type.OrderId
import playground.booookstore.query.type.ShopId

class OrderQueryGateway(private val rdbOrderDriver: RdbOrderDriver) {

    suspend fun find(orderId: OrderId): OrderQueryModel = rdbOrderDriver.find(orderId).let {
        OrderQueryModel(id = orderId, orderDateTime = LocalDateTime.parse("2023-08-18T14:52:00.00"), shopId = "9870")
    }

}

data class OrderQueryModel(val id: OrderId, val orderDateTime: LocalDateTime, val shopId: ShopId) {
}
