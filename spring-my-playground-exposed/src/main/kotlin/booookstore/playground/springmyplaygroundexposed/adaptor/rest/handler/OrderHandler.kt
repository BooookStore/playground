package booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler

import arrow.core.None
import arrow.core.Some
import booookstore.playground.springmyplaygroundexposed.domain.Accepted
import booookstore.playground.springmyplaygroundexposed.domain.Canceled
import booookstore.playground.springmyplaygroundexposed.domain.Order
import booookstore.playground.springmyplaygroundexposed.domain.OrderStatus
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
            is Some -> ok().contentType(APPLICATION_JSON).body(orderOption.value.toView())
            None -> notFound().build()
        }
    }

    fun Order.toView(): Map<String, Any> {
        fun OrderStatus.toViewName() = when (this) {
            is Accepted -> "accepted"
            is Canceled -> "canceled"
        }

        return mapOf(
            "orderId" to id,
            "orderName" to name(),
            "currentStatus" to mapOf(
                "status" to status().toViewName(),
                "occurredOn" to status().occurredOn,
                "operateBy" to status().userId
            )
        )
    }


    fun createOrder(request: ServerRequest): ServerResponse {
        data class RequestBody(val id: String, val name: String)

        val (orderId, name) = request.body<RequestBody>()
        val id = SecurityContextHolder.getContext().authentication.name.let(UUID::fromString)!!
        val order = Order.acceptNewOrder(orderId, name, id)
        orderUsecase.createOrder(order, id)
        return accepted().build()
    }

    fun cancelOrder(request: ServerRequest): ServerResponse {
        val orderId = request.pathVariable("id")
        val cancelUserId = SecurityContextHolder.getContext().authentication.name.let(UUID::fromString)!!
        orderUsecase.cancelOrder(orderId, cancelUserId)
        return accepted().build()
    }

}