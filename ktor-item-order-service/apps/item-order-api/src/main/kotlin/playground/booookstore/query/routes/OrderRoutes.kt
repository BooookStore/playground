package playground.booookstore.query.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import playground.booookstore.query.driver.RdbOrderDriver
import playground.booookstore.query.gateway.ItemQueryGateway
import playground.booookstore.query.gateway.OrderQueryGateway
import playground.booookstore.query.usecase.OrderQueryUsecase

fun Route.queryOrderRouting() {
    route("/order") {
        get("{id}") {
            val orderId = call.parameters["id"] ?: return@get call.respondText(
                status = HttpStatusCode.BadRequest,
                text = "Missing id",
            )
            OrderQueryUsecase(OrderQueryGateway(RdbOrderDriver()), ItemQueryGateway()).execute(orderId)
                .let { call.respond(it) }
        }
    }
}
