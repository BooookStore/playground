package booookstore.playground.spring.my.playground.order.api.adaptor.rest

import booookstore.playground.spring.my.playground.order.api.adaptor.rest.handler.OrderHandler
import booookstore.playground.spring.my.playground.order.api.adaptor.rest.handler.OrderListViewHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

@Configuration
class Router(
    val orderHandler: OrderHandler,
    val orderListViewHandler: OrderListViewHandler,
) {

    @Bean
    fun routerFunction(): RouterFunction<ServerResponse> = router {
        "order".nest {
            GET("{id}", orderHandler::findById)
            GET(param("createUser") { true }, orderListViewHandler::searchByCreatedUserId)
            POST(accept(APPLICATION_JSON), orderHandler::acceptOrder)
            PUT("{id}/cancel", orderHandler::cancelOrder)
        }
    }

}
