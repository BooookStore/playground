package playground.booookstore.query.driver

import kotlinx.coroutines.delay
import playground.booookstore.query.type.OrderId

class RdbOrderDriver {
    suspend fun find(orderId: OrderId): Map<String, String> {
        delay(3000L)
        return mapOf(
            "id" to "12345",
            "orderDateTime" to "2023-08-10 21:12",
        )
    }

}
