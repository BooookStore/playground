package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import org.jetbrains.exposed.sql.Table

object SubjectTable : Table("subject") {
    val mailAddress = reference("mail_address", UserTable.mailAddress)
    val role = reference("role", RoleTable.id)
    override val primaryKey = PrimaryKey(mailAddress, role)
}
