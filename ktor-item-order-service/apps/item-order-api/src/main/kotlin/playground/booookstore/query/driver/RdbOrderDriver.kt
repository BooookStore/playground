package playground.booookstore.query.driver

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import playground.booookstore.query.driver.dao.DatabaseFactory.dbQuery
import playground.booookstore.query.driver.dao.OrderTable
import playground.booookstore.query.driver.dao.OrderTableRow
import playground.booookstore.query.type.OrderId

class RdbOrderDriver {

    suspend fun find(orderId: OrderId) = dbQuery {
        OrderTable.selectAll().map(::mapToOrderTableRow).first()
    }

    private fun mapToOrderTableRow(row: ResultRow) = OrderTableRow(
        id = row[OrderTable.id]
    )

}
