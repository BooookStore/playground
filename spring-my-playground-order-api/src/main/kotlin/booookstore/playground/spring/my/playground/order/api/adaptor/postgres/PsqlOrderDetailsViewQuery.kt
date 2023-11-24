package booookstore.playground.spring.my.playground.order.api.adaptor.postgres

import arrow.core.Option
import arrow.core.firstOrNone
import booookstore.playground.spring.my.playground.order.api.adaptor.postgres.table.OrderStatusTable
import booookstore.playground.spring.my.playground.order.api.adaptor.postgres.table.OrderTable
import booookstore.playground.spring.my.playground.order.api.query.OrderDetailsView
import booookstore.playground.spring.my.playground.order.api.query.OrderDetailsViewQuery
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class PsqlOrderDetailsViewQuery : OrderDetailsViewQuery {

    override fun searchById(orderId: String): Option<OrderDetailsView> = OrderTable
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