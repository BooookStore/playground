package playground.booookstore.query.driver.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object OrderTable : Table() {
    val id = uuid("id")
    val orderDateTime = datetime("order_date_time")
    val shopId = varchar("shop_id", length = 50)
}