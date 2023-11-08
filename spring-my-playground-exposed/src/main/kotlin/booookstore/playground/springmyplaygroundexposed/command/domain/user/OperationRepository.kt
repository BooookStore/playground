package booookstore.playground.springmyplaygroundexposed.command.domain.user

import org.jetbrains.exposed.sql.statements.InsertStatement

interface OperationRepository {
    fun findByUserMailAddress(mailAddress: MailAddress): List<Operation>
    fun findByRoleId(roleId: RoleId): List<Operation>
    fun findByPermissionId(id: PermissionId): List<Operation>
    fun saveAsNew(operation: Operation): InsertStatement<Number>
}