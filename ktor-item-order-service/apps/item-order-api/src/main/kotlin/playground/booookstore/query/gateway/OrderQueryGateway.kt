package playground.booookstore.query.gateway

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import playground.booookstore.query.driver.RdbOrderDriver
import playground.booookstore.query.type.OrderId

class OrderQueryGateway(private val rdbOrderDriver: RdbOrderDriver) {

    suspend fun find(orderId: OrderId): OrderQueryModel {
        val orderMap = rdbOrderDriver.find(orderId)
        return OrderQueryModel(id = orderMap["id"]!!, orderDateTime = LocalDateTime(2023, Month.AUGUST, 10, 21, 12))
    }

}

data class OrderQueryModel(val id: OrderId, val orderDateTime: LocalDateTime)
