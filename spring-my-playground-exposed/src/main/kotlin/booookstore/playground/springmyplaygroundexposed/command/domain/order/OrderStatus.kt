package booookstore.playground.springmyplaygroundexposed.command.domain.order

import booookstore.playground.springmyplaygroundexposed.command.domain.user.UserId
import java.time.LocalDateTime

sealed class OrderStatus(val userId: UserId, val occurredOn: LocalDateTime)

class Accepted(userId: UserId, occurredOn: LocalDateTime) : OrderStatus(userId, occurredOn) {

    fun cancel(userId: UserId, occurredOn: LocalDateTime) = Canceled(userId, occurredOn)

}

class Canceled(userId: UserId, occurredOn: LocalDateTime) : OrderStatus(userId, occurredOn)