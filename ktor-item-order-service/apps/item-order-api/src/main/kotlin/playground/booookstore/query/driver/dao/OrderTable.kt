package playground.booookstore.query.driver.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object OrderTable : Table() {
    val id = uuid("id")
    val orderDateTime = datetime("order_date_time")
    val shopId = varchar("shop_id", length = 50)
}

data class OrderTableRow(val id: UUID, val orderDateTime: LocalDateTime, val shopId: String)