package booookstore.playground.spring.my.playground.order.api

import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.jetbrains.exposed.sql.DatabaseConfig
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ImportAutoConfiguration(value = [ExposedAutoConfiguration::class])
class ExposedConfig {

    @Bean
    fun databaseConfig() = DatabaseConfig {
        useNestedTransactions = true
    }

}