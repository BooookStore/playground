package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import booookstore.playground.springmyplaygroundexposed.domain.Permission
import org.jetbrains.exposed.sql.insert

class PermissionRepository {

    fun insert(permission: Permission) {
        PermissionTable.insert {
            it[PermissionTable.id] = permission.id
            it[PermissionTable.name] = permission.name
        }
        permission.operations.forEach { operation ->
            PermissionOperationTable.insert {
                it[PermissionOperationTable.permission] = permission.id
                it[PermissionOperationTable.operation] = operation
            }
        }
    }

}