package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object OrderHistoryTable : Table("order_history") {
    val order = reference("order", OrderTable.id)
    val datetime = datetime("datetime")
    val status = varchar("status", 255)
    val user = reference("user", UserTable.id)
}