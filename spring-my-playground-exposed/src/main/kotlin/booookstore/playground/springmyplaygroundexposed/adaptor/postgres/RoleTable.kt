package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import org.jetbrains.exposed.sql.Table

object RoleTable : Table("role") {
    val id = varchar("id", 255)
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}