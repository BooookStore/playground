package booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler

import booookstore.playground.springmyplaygroundexposed.query.OrderListViewQuery
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import java.util.*

@Component
@Transactional(readOnly = true)
class OrderListViewHandler(private val orderListViewQuery: OrderListViewQuery) {

    fun searchByCreatedUserId(serverRequest: ServerRequest): ServerResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        val userId = UUID.fromString(authentication.name)
        val orderListView = orderListViewQuery.searchByCreatedUserId(userId)
        return ok().contentType(APPLICATION_JSON).body(orderListView)
    }

}