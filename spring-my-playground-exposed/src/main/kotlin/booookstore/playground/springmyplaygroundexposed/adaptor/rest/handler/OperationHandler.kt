package booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler

import booookstore.playground.springmyplaygroundexposed.usecase.OperationUsecase
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.*

@Component
class OperationHandler(private val operationUsecase: OperationUsecase) {

    fun findOperationByRoleId(serverRequest: ServerRequest): ServerResponse {
        val operations = operationUsecase.findOperationByRoleId(serverRequest.pathVariable("id"))
        return ok().contentType(MediaType.APPLICATION_JSON).body(operations)
    }

}