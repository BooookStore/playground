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
        val operations = OperationRepository().findByPermissionId("PE_001")
        logger.info("find permission {}", operations)
    }

}

data class Operation(
    val id: String,
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

object OperationTable : Table("operation") {
    val id = varchar("id", 255)
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}

object PermissionOperationTable : Table("permission_operation") {
    val permission = reference("permission", PermissionTable.id)
    val operation = reference("operation", OperationTable.id)

    init {
        index(isUnique = true, permission, operation)
    }
}

object PermissionTable : Table("permission") {
    val id = varchar("id", 255)
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}