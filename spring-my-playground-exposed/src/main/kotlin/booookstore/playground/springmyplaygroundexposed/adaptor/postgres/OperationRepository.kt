package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import booookstore.playground.springmyplaygroundexposed.domain.Operation
import booookstore.playground.springmyplaygroundexposed.domain.PermissionId
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class OperationRepository {

    fun insert(operation: Operation) = OperationTable.insert {
        it[OperationTable.id] = operation.id
        it[OperationTable.name] = operation.name
    }

    fun findByPermissionId(id: PermissionId): List<Operation> =
        (PermissionTable innerJoin PermissionOperationTable innerJoin OperationTable)
            .select { PermissionTable.id eq id }
            .map { Operation(it[OperationTable.id], it[OperationTable.name]) }

}