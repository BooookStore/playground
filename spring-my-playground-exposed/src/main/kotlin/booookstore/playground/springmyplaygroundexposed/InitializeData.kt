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
        logger.info("start initialize data")

        OperationRepository().insert(Operation("OP0001", "DISPLAY_ORDER"))
        OperationRepository().insert(Operation("OP0002", "CANCEL_ORDER"))

//        PermissionTable.insert {
//            it[id] = "PE0001"
//            it[name] = "ORDER_CANCELABLE"
//        }
//
//        PermissionOperationTable.insert {
//            it[permission] = "PE0001"
//            it[operation] = "OP0001"
//        }
//        PermissionOperationTable.insert {
//            it[permission] = "PE0001"
//            it[operation] = "OP0002"
//        }

        OperationRepository().findByPermissionId("PE0001").let { println(it) }

        logger.info("complete initialize data")
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
            .select { PermissionTable.id eq "PE0001" }
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