package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import org.jetbrains.exposed.sql.Table

object UserTable : Table("user") {
    val id = uuid("id")
    val mailAddress = varchar("mail_address", 255)
    val password = varchar("password", 255)
    override val primaryKey = PrimaryKey(id)
}
