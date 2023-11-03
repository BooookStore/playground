package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import org.jetbrains.exposed.sql.Table

object SubjectTable : Table("subject") {
    val user = reference("user", UserTable.id)
    val role = reference("role", RoleTable.id)
    override val primaryKey = PrimaryKey(user, role)
}
