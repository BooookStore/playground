package booookstore.playground.spring.my.playground.order.api.adaptor.postgres

import arrow.core.None
import arrow.core.Some
import arrow.core.firstOrNone
import booookstore.playground.spring.my.playground.order.api.adaptor.postgres.table.OrderStatusTable
import booookstore.playground.spring.my.playground.order.api.adaptor.postgres.table.OrderTable
import booookstore.playground.spring.my.playground.order.api.query.OrderListView
import booookstore.playground.spring.my.playground.order.api.query.OrderListViewQuery
import org.jetbrains.exposed.sql.SortOrder.DESC
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PsqlOrderListViewQuery : OrderListViewQuery {

    override fun searchByCreatedUserId(userId: UUID) =
        OrderTable
            .select { OrderTable.create_user eq userId }
            .map {
                OrderListView(
                    it[OrderTable.id],
                    it[OrderTable.name],
                    findAcceptedDate(it[OrderTable.id]),
                    fetchCurrentStatus(it[OrderTable.id])
                )
            }
            .toList()

    private fun fetchCurrentStatus(orderId: String) =
        OrderStatusTable
            .select { OrderStatusTable.order eq orderId }
            .orderBy(OrderStatusTable.datetime, DESC)
            .map { it[OrderStatusTable.status].toString() }
            .firstOrNone().let {
                when (it) {
                    None -> "data not found"
                    is Some -> it.value
                }
            }

    private fun findAcceptedDate(orderId: String) =
        OrderStatusTable
            .select { (OrderStatusTable.order eq orderId) and (OrderStatusTable.status eq "ACCEPTED") }
            .map { it[OrderStatusTable.datetime].toString() }
            .firstOrNone().let {
                when (it) {
                    None -> "data not found"
                    is Some -> it.value
                }
            }

}