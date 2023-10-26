package booookstore.playground.springmyplaygrounddatajdbctemplate.domain.coffee

import org.springframework.data.repository.CrudRepository

interface CoffeeRepository : CrudRepository<Coffee, String>