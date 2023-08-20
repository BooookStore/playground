package playground.booookstore.query.driver.dao

import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {

    fun initDatabase(config: ApplicationConfig) = Database.connect(
        url = config.property("database.jdbcURL").getString(),
        driver = config.property("database.driverClassName").getString(),
        user = config.property("database.user").getString(),
        password = config.property("database.user").getString(),
    )

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}