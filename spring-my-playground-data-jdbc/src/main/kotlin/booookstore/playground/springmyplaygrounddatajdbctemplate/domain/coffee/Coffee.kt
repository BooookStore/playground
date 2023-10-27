package booookstore.playground.springmyplaygrounddatajdbctemplate.domain.coffee

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Embedded

data class Coffee(
    @Id
    val id: String,
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    val coffeeBeans: CoffeeBeans
)

data class CoffeeBeans(
    val beans: Set<CoffeeBean>
)

data class CoffeeBean(
    val name: String
)