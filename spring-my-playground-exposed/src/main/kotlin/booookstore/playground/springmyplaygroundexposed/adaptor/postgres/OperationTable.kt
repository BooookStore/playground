package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import org.jetbrains.exposed.sql.Table

object OperationTable : Table("operation") {
    val id = varchar("id", 255)
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}