package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import booookstore.playground.springmyplaygroundexposed.domain.Operation
import booookstore.playground.springmyplaygroundexposed.domain.PermissionId
import booookstore.playground.springmyplaygroundexposed.domain.RoleId
import org.jetbrains.exposed.sql.select

class OperationRepository {

    fun findByRoleId(roleId: RoleId): List<Operation> =
        (RoleTable
                innerJoin RolePermissionTable
                innerJoin PermissionTable
                innerJoin PermissionOperationTable
                innerJoin OperationTable)
            .select { RoleTable.id eq roleId }
            .map { Operation(it[OperationTable.id], it[OperationTable.name]) }

    fun findByPermissionId(id: PermissionId): List<Operation> =
        (PermissionTable
                innerJoin PermissionOperationTable
                innerJoin OperationTable)
            .select { PermissionTable.id eq id }
            .map { Operation(it[OperationTable.id], it[OperationTable.name]) }

}