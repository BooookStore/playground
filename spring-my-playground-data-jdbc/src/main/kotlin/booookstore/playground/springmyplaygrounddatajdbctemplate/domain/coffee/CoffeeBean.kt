package booookstore.playground.springmyplaygrounddatajdbctemplate.domain.coffee

import org.springframework.data.relational.core.mapping.Table

@Table
data class CoffeeBean(
    val name: String
)