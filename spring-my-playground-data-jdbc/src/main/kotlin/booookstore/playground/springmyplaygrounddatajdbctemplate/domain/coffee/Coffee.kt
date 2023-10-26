package booookstore.playground.springmyplaygrounddatajdbctemplate.domain.coffee

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table
data class Coffee(
    @Id
    val id: String,
    val coffeeBean: CoffeeBean
)
