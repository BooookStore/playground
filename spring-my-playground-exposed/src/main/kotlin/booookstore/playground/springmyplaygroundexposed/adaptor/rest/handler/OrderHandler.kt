package booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler

import arrow.core.None
import arrow.core.Some
import booookstore.playground.springmyplaygroundexposed.command.domain.order.Order
import booookstore.playground.springmyplaygroundexposed.command.usecase.OrderUsecase
import booookstore.playground.springmyplaygroundexposed.query.OrderDetailsViewQuery
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.*
import org.springframework.web.servlet.function.body
import java.util.*

@Component
class OrderHandler(val orderUsecase: OrderUsecase, val orderDetailsViewQuery: OrderDetailsViewQuery) {

    fun findById(request: ServerRequest): ServerResponse {
        val orderId = request.pathVariable("id")
        return when (val orderOption = orderDetailsViewQuery.searchById(orderId)) {
            is Some -> ok().contentType(APPLICATION_JSON).body(orderOption.value)
            None -> notFound().build()
        }
    }

    fun acceptOrder(request: ServerRequest): ServerResponse {
        data class RequestBody(val id: String, val name: String)

        val (orderId, name) = request.body<RequestBody>()
        val id = SecurityContextHolder.getContext().authentication.name.let(UUID::fromString)!!
        val order = Order.acceptNewOrder(orderId, id, name)
        orderUsecase.acceptOrder(order, id)
        return accepted().build()
    }

    fun cancelOrder(request: ServerRequest): ServerResponse {
        val orderId = request.pathVariable("id")
        val cancelUserId = SecurityContextHolder.getContext().authentication.name.let(UUID::fromString)!!
        orderUsecase.cancelOrder(orderId, cancelUserId)
        return accepted().build()
    }

}