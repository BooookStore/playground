package playground.booookstore.query.driver.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {

    fun initDatabase(config: ApplicationConfig): Database {
        val dataSource = HikariConfig().apply {
            jdbcUrl = config.property("database.jdbcURL").getString()
            username = config.property("database.user").getString()
            password = config.property("database.password").getString()
        }.let { HikariDataSource(it) }
        return Database.connect(dataSource)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}