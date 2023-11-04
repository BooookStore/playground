package booookstore.playground.springmyplaygroundexposed.domain

import java.time.LocalDateTime.now

typealias OrderId = String

data class Order(val id: String, val name: String, var status: OrderStatus) {

    companion object {

        fun acceptNewOrder(id: String, name: String, userId: UserId) =
            Order(id, name, Accepted(userId, now()))

    }

    fun cancel(userId: UserId): Unit = when (val currentStatus = status) {
        is Accepted -> currentStatus.cancel(userId, now()).let { status = it }
        is Canceled -> Unit
    }

}