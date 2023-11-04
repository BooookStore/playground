package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import arrow.core.None
import arrow.core.Some
import arrow.core.firstOrNone
import booookstore.playground.springmyplaygroundexposed.domain.Order
import booookstore.playground.springmyplaygroundexposed.domain.OrderId
import booookstore.playground.springmyplaygroundexposed.domain.OrderStatus
import booookstore.playground.springmyplaygroundexposed.domain.UserId
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
                OrderStatus.valueOf(it[OrderStatusTable.status])
            )
        }
        .firstOrNone()

    fun saveAsNew(order: Order, userId: UserId) {
        OrderTable.insert {
            it[id] = order.id
            it[name] = order.name
        }
        OrderStatusTable.insert {
            it[this.order] = order.id
            it[datetime] = LocalDateTime.now()
            it[status] = order.status.toString()
            it[this.user] = userId
        }
    }

    fun saveAsOverride(updatedOrder: Order, userId: UserId) {
        when (val orderOption = findById(updatedOrder.id)) {
            is None -> throw Exception()
            is Some -> updateSavedOrder(orderOption.value, updatedOrder, userId)
        }
    }

    private fun updateSavedOrder(savedOrder: Order, updatedOrder: Order, userId: UserId) {
        OrderTable.update(where = { OrderTable.id eq updatedOrder.id }) {
            it[name] = updatedOrder.name
        }
        if (savedOrder isChangedStatus updatedOrder) {
            OrderStatusTable.insert {
                it[this.order] = updatedOrder.id
                it[datetime] = LocalDateTime.now()
                it[status] = updatedOrder.status.toString()
                it[this.user] = userId
            }
        }
    }

    private infix fun Order.isChangedStatus(other: Order) = status != other.status

}