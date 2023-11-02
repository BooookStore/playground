package booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler

import arrow.core.None
import arrow.core.Some
import booookstore.playground.springmyplaygroundexposed.usecase.OrderUsecase
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.notFound
import org.springframework.web.servlet.function.ServerResponse.ok

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

}