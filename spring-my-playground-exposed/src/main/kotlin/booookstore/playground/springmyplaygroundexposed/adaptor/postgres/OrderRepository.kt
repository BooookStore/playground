package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import arrow.core.firstOrNone
import booookstore.playground.springmyplaygroundexposed.domain.Order
import booookstore.playground.springmyplaygroundexposed.domain.OrderId
import booookstore.playground.springmyplaygroundexposed.domain.UserId
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class OrderRepository {

    fun findById(orderId: OrderId) = OrderTable
        .select { OrderTable.id eq orderId }
        .map { Order(it[OrderTable.id], it[OrderTable.name], TODO()) }
        .firstOrNone()

    fun saveAsNew(order: Order, userId: UserId) {
        OrderTable.insert {
            it[id] = order.id
            it[name] = order.name
        }
        OrderHistoryTable.insert {
            it[this.order] = order.id
            it[datetime] = LocalDateTime.now()
            it[status] = order.status.toString()
            it[this.user] = userId
        }
    }

}