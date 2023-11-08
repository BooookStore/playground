package booookstore.playground.springmyplaygroundexposed.query

import arrow.core.Option
import arrow.core.firstOrNone
import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.OrderStatusTable
import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.OrderTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class OrderDetailsViewQuery {

    fun searchById(orderId: String): Option<OrderDetailsView> = OrderTable
        .select { OrderTable.id eq orderId }
        .limit(1)
        .map {
            OrderDetailsView(
                orderId = it[OrderTable.id],
                createUser = it[OrderTable.create_user].toString(),
                name = it[OrderTable.name],
                statusHistory = searchOrderStatusView(orderId)
            )
        }
        .firstOrNone()

    private fun searchOrderStatusView(orderId: String) = OrderStatusTable
        .select { OrderStatusTable.order eq orderId }
        .orderBy(OrderStatusTable.datetime, SortOrder.ASC)
        .map {
            OrderDetailsView.OrderStatusView(
                status = it[OrderStatusTable.status],
                occurredOn = it[OrderStatusTable.datetime],
                user = it[OrderStatusTable.user].toString()
            )
        }

}