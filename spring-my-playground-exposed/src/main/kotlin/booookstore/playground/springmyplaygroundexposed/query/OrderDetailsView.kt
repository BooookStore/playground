package booookstore.playground.springmyplaygroundexposed.query

import java.time.LocalDateTime

data class OrderDetailsView(
    val orderId: String,
    val createUser: String,
    val name: String,
    val statusHistory: List<OrderStatusView>
) {

    data class OrderStatusView(
        val status: String,
        val occurredOn: LocalDateTime,
        val user: String
    )

}