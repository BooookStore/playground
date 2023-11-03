package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import arrow.core.firstOrNone
import booookstore.playground.springmyplaygroundexposed.domain.Order
import booookstore.playground.springmyplaygroundexposed.domain.OrderId
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class OrderRepository {

    fun findById(orderId: OrderId) = OrderTable
        .select { OrderTable.id eq orderId }
        .map { Order(it[OrderTable.id], it[OrderTable.name]) }
        .firstOrNone()

    fun saveAsNew(order: Order) = OrderTable.insert {
        it[id] = order.id
        it[name] = order.name
    }

}