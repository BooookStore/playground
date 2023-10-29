package booookstore.playground.springmyplaygroundexposed

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InitializeData : ApplicationRunner {

    private val logger = LoggerFactory.getLogger(InitializeData::class.java)

    @Transactional
    override fun run(args: ApplicationArguments?) {
        OperationRepository().findByPermissionId("PE_001").let {
            logger.info("find permission {}", it)
        }
        OperationRepository().findByPermissionId("PE_002").let {
            logger.info("find permission {}", it)
        }

        Permission("PE_003", "VIEW_ALL_ORDER", setOf("OP_D001")).let {
            PermissionRepository().insert(it)
        }
    }

}

typealias PermissionId = String
typealias OperationId = String

data class Permission(
    val id: PermissionId,
    val name: String,
    val operations: Set<OperationId>
)

data class Operation(
    val id: OperationId,
    val name: String
)

class OperationRepository {

    fun insert(operation: Operation) = OperationTable.insert {
        it[id] = operation.id
        it[name] = operation.name
    }

    fun findByPermissionId(id: String): List<Operation> =
        (PermissionTable innerJoin PermissionOperationTable innerJoin OperationTable)
            .select { PermissionTable.id eq id }
            .map { Operation(it[OperationTable.id], it[OperationTable.name]) }

}

class PermissionRepository {

    fun insert(permission: Permission) {
        PermissionTable.insert {
            it[id] = permission.id
            it[name] = permission.name
        }
        permission.operations.forEach { operation ->
            PermissionOperationTable.insert {
                it[PermissionOperationTable.permission] = permission.id
                it[PermissionOperationTable.operation] = operation
            }
        }
    }

}

object OperationTable : Table("operation") {
    val id = varchar("id", 255)
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}

object PermissionOperationTable : Table("permission_operation") {
    val permission = reference("permission", PermissionTable.id)
    val operation = reference("operation", OperationTable.id)
    override val primaryKey = PrimaryKey(permission, operation)
}

object PermissionTable : Table("permission") {
    val id = varchar("id", 255)
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}