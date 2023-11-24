package booookstore.playground.spring.my.playground.order.api.adaptor.postgres.table

import org.jetbrains.exposed.sql.Table

object OrderTable : Table("order") {
    val id = varchar("id", 255)
    val create_user = uuid("create_user")
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}