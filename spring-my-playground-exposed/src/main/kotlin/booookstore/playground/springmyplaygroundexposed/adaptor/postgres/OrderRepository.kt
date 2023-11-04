package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import arrow.core.firstOrNone
import booookstore.playground.springmyplaygroundexposed.domain.Order
import booookstore.playground.springmyplaygroundexposed.domain.OrderId
import booookstore.playground.springmyplaygroundexposed.domain.OrderStatus
import booookstore.playground.springmyplaygroundexposed.domain.UserId
import org.jetbrains.exposed.sql.SortOrder.DESC
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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

}