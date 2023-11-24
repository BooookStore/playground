package booookstore.playground.spring.my.playground.order.api.adaptor.rest.handler

import arrow.core.None
import arrow.core.Some
import booookstore.playground.spring.my.playground.order.api.command.domain.Order
import booookstore.playground.spring.my.playground.order.api.command.usecase.OrderUsecase
import booookstore.playground.spring.my.playground.order.api.query.OrderDetailsViewQuery
import org.springframework.http.MediaType.APPLICATION_JSON
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
        data class RequestBody(val id: String, val userId: UUID, val name: String)
        val (orderId, userId, name) = request.body<RequestBody>()

        val order = Order.acceptNewOrder(orderId, userId, name)
        orderUsecase.acceptOrder(order, userId)
        return accepted().build()
    }

    fun cancelOrder(request: ServerRequest): ServerResponse {
        data class RequestBody(val userId: UUID)
        val (userId) = request.body<RequestBody>()

        val orderId = request.pathVariable("id")
        orderUsecase.cancelOrder(orderId, userId)
        return accepted().build()
    }

}