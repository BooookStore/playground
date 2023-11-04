package booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler

import arrow.core.None
import arrow.core.Some
import booookstore.playground.springmyplaygroundexposed.domain.Order
import booookstore.playground.springmyplaygroundexposed.usecase.OrderUsecase
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.*
import org.springframework.web.servlet.function.body
import java.util.*

@Component
class OrderHandler(val orderUsecase: OrderUsecase) {

    fun findById(request: ServerRequest): ServerResponse {
        val orderId = request.pathVariable("id")
        val orderOption = orderUsecase.findById(orderId)

        return when (orderOption) {
            is Some -> ok().contentType(APPLICATION_JSON).body(orderOption.value)
            None -> notFound().build()
        }
    }

    fun createOrder(request: ServerRequest): ServerResponse {
        data class RequestBody(val id: String, val name: String)

        val (orderId, name) = request.body<RequestBody>()
        val order = Order.acceptNewOrder(orderId, name)
        val id = SecurityContextHolder.getContext().authentication.name.let(UUID::fromString)!!
        orderUsecase.createOrder(order, id)
        return accepted().build()
    }

    fun cancelOrder(request: ServerRequest): ServerResponse {
        val orderId = request.pathVariable("id")
        val cancelUserId = SecurityContextHolder.getContext().authentication.name(UUID::fromString)!!
        orderUsecase.cancelOrder(orderId, cancelUserId)
        return accepted().build()
    }

}