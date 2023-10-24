package booookstore.playground.springmyplaygrounddatajdbctemplate.adapter.jdbctemplate

import booookstore.playground.springmyplaygrounddatajdbctemplate.domain.coffee.Coffee
import booookstore.playground.springmyplaygrounddatajdbctemplate.domain.coffee.CoffeeRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class CoffeeJdbcTemplateRepository(val jdbcTemplate: JdbcTemplate) : CoffeeRepository {

    override fun findAll(): Iterable<Coffee> =
        jdbcTemplate.query("select id from coffee", ::mapRowToCoffee)

    override fun findById(id: String): Coffee? =
        jdbcTemplate.query("select id from coffee where id = :id", ::mapRowToCoffee, id).firstOrNull()

    override fun insert(coffee: Coffee) {
        jdbcTemplate.update("insert into coffee (id) values (?)", coffee.id)
    }

    private fun mapRowToCoffee(row: ResultSet, rowNum: Int) = Coffee(
        id = row.getString("id")
    )

}