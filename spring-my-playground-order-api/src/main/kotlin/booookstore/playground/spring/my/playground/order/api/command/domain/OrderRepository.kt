package booookstore.playground.spring.my.playground.order.api.command.domain

import arrow.core.Option
import java.time.LocalDateTime

interface OrderRepository {
    fun findById(orderId: OrderId): Option<Order>
    fun saveAsNew(order: Order, userId: UserId)
    fun saveAsOverride(updatedOrder: Order): Option<Order>

    infix fun Order.statusIsChange(other: Order): Boolean
    fun OrderStatus.toStatusName(): String
    fun orderStatusFrom(statusName: String, userId: UserId, occurredOn: LocalDateTime): OrderStatus
}