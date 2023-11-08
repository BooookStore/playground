package booookstore.playground.springmyplaygroundexposed.command.domain

import booookstore.playground.springmyplaygroundexposed.command.domain.user.UserId
import java.time.LocalDateTime.now

typealias OrderId = String

class Order(val id: OrderId, val createUser: UserId, private var name: String, private var status: OrderStatus) {

    fun name() = name

    fun status() = status

    companion object {

        fun acceptNewOrder(id: String, createUserId: UserId, name: String) =
            Order(id, createUserId, name, Accepted(createUserId, now()))

    }

    fun cancel(userId: UserId): Unit = when (val currentStatus = status) {
        is Accepted -> currentStatus.cancel(userId, now()).let { status = it }
        is Canceled -> Unit
    }

}