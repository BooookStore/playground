package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.table.OrderTable
import booookstore.playground.springmyplaygroundexposed.query.OrderListView
import booookstore.playground.springmyplaygroundexposed.query.OrderListViewQuery
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PsqlOrderListViewQuery : OrderListViewQuery {

    override fun searchByCreatedUserId(userId: UUID) =
        OrderTable
            .select { OrderTable.create_user eq userId }
            .map { OrderListView(it[OrderTable.name], "", "") }
            .toList()

}