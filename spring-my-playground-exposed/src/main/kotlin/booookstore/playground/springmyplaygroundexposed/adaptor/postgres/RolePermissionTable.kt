package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import org.jetbrains.exposed.sql.Table

object RolePermissionTable : Table("role_permission") {
    val role = reference("role", RoleTable.id)
    val permission = reference("permission", PermissionTable.id)
    override val primaryKey = PrimaryKey(role, permission)
}