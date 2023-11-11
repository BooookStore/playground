package booookstore.playground.springmyplaygroundexposed.adaptor.postgres.table

import org.jetbrains.exposed.sql.Table

object OrderTable : Table("order") {
    val id = varchar("id", 255)
    val create_user = reference("create_user", UserTable.id)
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}