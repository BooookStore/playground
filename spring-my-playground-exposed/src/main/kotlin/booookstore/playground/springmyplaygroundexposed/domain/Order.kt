package booookstore.playground.springmyplaygroundexposed.domain

import java.time.LocalDateTime.now

typealias OrderId = String

class Order(val id: String, private var name: String, private var status: OrderStatus) {

    fun name() = name

    fun status() = status

    companion object {

        fun acceptNewOrder(id: String, name: String, userId: UserId) =
            Order(id, name, Accepted(userId, now()))

    }

    fun cancel(userId: UserId): Unit = when (val currentStatus = status) {
        is Accepted -> currentStatus.cancel(userId, now()).let { status = it }
        is Canceled -> Unit
    }

}