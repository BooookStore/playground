package playground.booookstore.query.driver

import kotlinx.coroutines.delay
import playground.booookstore.query.type.OrderId

class RdbOrderDriver {
    suspend fun find(orderId: OrderId): Map<String, String> {
        delay(3000L)
        return mapOf(
            "id" to orderId,
            "orderDateTime" to "2023-08-10T21:12:00.00",
        )
    }

}
