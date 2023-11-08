package booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler

import booookstore.playground.springmyplaygroundexposed.command.domain.user.Operation
import booookstore.playground.springmyplaygroundexposed.command.usecase.OperationUsecase
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.accepted
import org.springframework.web.servlet.function.ServerResponse.ok
import org.springframework.web.servlet.function.body

@Component
class OperationHandler(private val operationUsecase: OperationUsecase) {

    fun findOperationByRoleId(serverRequest: ServerRequest): ServerResponse {
        val operations = operationUsecase.findOperationByRoleId(serverRequest.param("roleId").get())
        return ok().contentType(APPLICATION_JSON).body(operations)
    }

    fun findOperationByUserMailAddress(serverRequest: ServerRequest): ServerResponse {
        val operations = operationUsecase.findOperationByUserMailAddress(serverRequest.param("userMailAddress").get())
        return ok().contentType(APPLICATION_JSON).body(operations)
    }

    fun createOperation(serverRequest: ServerRequest): ServerResponse {
        val operation = serverRequest.body<Operation>()
        operationUsecase.createOperation(operation)
        return accepted().build()
    }

}