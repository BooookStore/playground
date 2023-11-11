package booookstore.playground.springmyplaygroundexposed.adaptor.postgres.table

import org.jetbrains.exposed.sql.Table

object PermissionOperationTable : Table("permission_operation") {
    val permission = reference("permission", PermissionTable.id)
    val operation = reference("operation", OperationTable.id)
    override val primaryKey = PrimaryKey(permission, operation)
}