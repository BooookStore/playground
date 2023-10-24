package booookstore.playground.springmyplaygrounddatajdbctemplate.adapter.rest

import booookstore.playground.springmyplaygrounddatajdbctemplate.domain.coffee.Coffee
import booookstore.playground.springmyplaygrounddatajdbctemplate.domain.coffee.CoffeeRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("coffee")
class CoffeeRestController(val coffeeRepository: CoffeeRepository) {

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<Coffee> {
        return coffeeRepository.findAll().toList()
    }

}