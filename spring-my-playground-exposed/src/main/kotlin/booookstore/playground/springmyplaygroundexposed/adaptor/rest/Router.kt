package booookstore.playground.springmyplaygroundexposed.adaptor.rest

import booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler.OperationHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.*
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

@Configuration
class Router {

    @Bean
    fun routerFunction(handler: OperationHandler): RouterFunction<ServerResponse> = router {
        "operation".nest {
            GET(param("roleId") { true }, handler::findOperationByRoleId)
            GET(param("userMailAddress") { true }, handler::findOperationByUserMailAddress)
            POST(accept(APPLICATION_JSON), handler::createOperation)
        }
    }

}
