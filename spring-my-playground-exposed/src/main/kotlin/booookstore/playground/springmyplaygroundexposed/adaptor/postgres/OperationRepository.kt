package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import booookstore.playground.springmyplaygroundexposed.domain.MailAddress
import booookstore.playground.springmyplaygroundexposed.domain.Operation
import booookstore.playground.springmyplaygroundexposed.domain.PermissionId
import booookstore.playground.springmyplaygroundexposed.domain.RoleId
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class OperationRepository {

    fun findByUserMailAddress(mailAddress: MailAddress): List<Operation> =
        (UserTable
                innerJoin SubjectTable
                innerJoin RoleTable
                innerJoin RolePermissionTable
                innerJoin PermissionTable
                innerJoin PermissionOperationTable
                innerJoin OperationTable)
            .select { UserTable.mailAddress eq mailAddress }
            .map { Operation(it[OperationTable.id], it[OperationTable.name]) }

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

    fun saveAsNew(operation: Operation) = OperationTable.insert {
        it[id] = operation.id
        it[name] = operation.name
    }

}