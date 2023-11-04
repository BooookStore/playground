package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import arrow.core.firstOrNone
import booookstore.playground.springmyplaygroundexposed.domain.*
import org.jetbrains.exposed.sql.SortOrder.DESC
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class OrderRepository {

    fun findById(orderId: OrderId) = (OrderTable innerJoin OrderStatusTable)
        .select { OrderTable.id eq orderId }
        .orderBy(OrderStatusTable.datetime, DESC)
        .limit(1)
        .map {
            Order(
                it[OrderTable.id],
                it[OrderTable.name],
                orderStatusFrom(
                    it[OrderStatusTable.status],
                    it[OrderStatusTable.user],
                    it[OrderStatusTable.datetime]
                )
            )
        }
        .firstOrNone()

    fun saveAsNew(order: Order, userId: UserId) {
        OrderTable.insert {
            it[id] = order.id
            it[name] = order.name()
        }
        OrderStatusTable.insert {
            it[this.order] = order.id
            it[datetime] = LocalDateTime.now()
            it[status] = order.status().toStatusName()
            it[this.user] = userId
        }
    }

    fun saveAsOverride(updatedOrder: Order, userId: UserId) = findById(updatedOrder.id)
        .onSome { updateSavedOrder(it, updatedOrder, userId) }
        .onNone { throw Exception("order can't save as override. order not found ${updatedOrder.id}") }

    private fun updateSavedOrder(savedOrder: Order, updatedOrder: Order, userId: UserId) {
        OrderTable.update(where = { OrderTable.id eq updatedOrder.id }) {
            it[name] = updatedOrder.name()
        }
        if (savedOrder statusIsChange updatedOrder) {
            OrderStatusTable.insert {
                it[this.order] = updatedOrder.id
                it[datetime] = LocalDateTime.now()
                it[status] = updatedOrder.status().toStatusName()
                it[this.user] = userId
            }
        }
    }

    private infix fun Order.statusIsChange(other: Order) = status() != other.status()

    private fun OrderStatus.toStatusName() = when (this) {
        is Accepted -> "ACCEPTED"
        is Canceled -> "CANCELED"
    }

    private fun orderStatusFrom(statusName: String, userId: UserId, occurredOn: LocalDateTime) = when (statusName) {
        "ACCEPTED" -> Accepted(userId, occurredOn)
        "CANCELED" -> Canceled(userId, occurredOn)
        else -> throw IllegalArgumentException("can't create order status from status name $statusName")
    }

}