package booookstore.playground.springmyplaygroundexposed.adaptor.postgres.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object OrderStatusTable : Table("order_status") {
    val order = reference("order", OrderTable.id)
    val datetime = datetime("datetime")
    val status = varchar("status", 255)
    val user = reference("user", UserTable.id)
}