package playground.booookstore.query.driver.dao

import org.jetbrains.exposed.sql.Table

object OrderTable : Table() {

    val id = varchar("id", 128)

}

data class OrderTableRow(val id: String)