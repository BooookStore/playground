package booookstore.playground.springmyplaygrounddatajdbctemplate.domain.coffee

interface CoffeeRepository {

    fun findAll(): Iterable<Coffee>

    fun findById(id: String): Coffee?

    fun insert(coffee: Coffee)

}