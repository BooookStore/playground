package booookstore.playground.springmyplaygroundexposed.adaptor.rest

import booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler.OperationHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

@Configuration
class Router {

    @Bean
    fun routerFunction(handler: OperationHandler): RouterFunction<ServerResponse> = router {
        GET("/operation/{id}", handler::findOperationByRoleId)
    }

}
