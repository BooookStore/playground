package playground.booookstore.query.driver.dao

import org.jetbrains.exposed.sql.Table

object OrderTable : Table() {

    val id = uuid("id")

}

data class OrderTableRow(val id: String)