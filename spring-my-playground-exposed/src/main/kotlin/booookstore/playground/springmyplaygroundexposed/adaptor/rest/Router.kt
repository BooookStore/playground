package booookstore.playground.springmyplaygroundexposed.adaptor.rest

import booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler.MeViewHandler
import booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler.OperationHandler
import booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler.OrderHandler
import booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler.OrderListViewHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

@Configuration
class Router(
    val operationHandler: OperationHandler,
    val orderHandler: OrderHandler,
    val meViewHandler: MeViewHandler,
    val orderListViewHandler: OrderListViewHandler,
) {

    @Bean
    fun routerFunction(): RouterFunction<ServerResponse> = router {
        "operation".nest {
            GET(param("roleId") { true }, operationHandler::findOperationByRoleId)
            GET(param("userMailAddress") { true }, operationHandler::findOperationByUserMailAddress)
            POST(accept(APPLICATION_JSON), operationHandler::createOperation)
        }
        "order".nest {
            GET(orderListViewHandler::searchByCreatedUserId)
            GET("{id}", orderHandler::findById)
            POST(accept(APPLICATION_JSON), orderHandler::acceptOrder)
            PUT("{id}/cancel", orderHandler::cancelOrder)
        }
        GET("me", meViewHandler::meView)
    }

}
