package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import org.jetbrains.exposed.sql.Table

object UserTable : Table("user") {
    val mailAddress = varchar("mail_address", 255)
    override val primaryKey = PrimaryKey(mailAddress)
}
