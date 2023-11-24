package booookstore.playground.spring.my.playground.order.api.adaptor.rest.handler

import booookstore.playground.spring.my.playground.order.api.query.OrderListViewQuery
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.badRequest
import org.springframework.web.servlet.function.ServerResponse.ok
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Component
@Transactional(readOnly = true)
class OrderListViewHandler(private val orderListViewQuery: OrderListViewQuery) {

    fun searchByCreatedUserId(request: ServerRequest): ServerResponse {
        val userId = request.param("createUser").getOrNull().let(UUID::fromString) ?: return badRequest().build()
        val orderListView = orderListViewQuery.searchByCreatedUserId(userId)
        return ok().contentType(APPLICATION_JSON).body(orderListView)
    }

}