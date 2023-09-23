package playground.booookstore.query.driver

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import playground.booookstore.query.driver.dao.DatabaseFactory.dbQuery
import playground.booookstore.query.driver.dao.OrderTable
import playground.booookstore.query.driver.dao.OrderTableRow
import playground.booookstore.query.type.OrderId

class RDBOrderDriver {

    suspend fun find(orderId: OrderId) = dbQuery {
        OrderTable.select { OrderTable.id eq orderId }
            .map(::mapToOrderTableRow)
            .singleOrNull()
    }

    private fun mapToOrderTableRow(row: ResultRow) = OrderTableRow(
        id = row[OrderTable.id]
    )

}
