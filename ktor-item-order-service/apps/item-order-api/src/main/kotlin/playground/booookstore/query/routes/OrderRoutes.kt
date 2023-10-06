package playground.booookstore.query.routes

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import playground.booookstore.query.driver.HttpClientShopDriver
import playground.booookstore.query.driver.RDBOrderDriver
import playground.booookstore.query.gateway.ItemQueryGateway
import playground.booookstore.query.gateway.OrderQueryGateway
import playground.booookstore.query.gateway.ShopQueryGateway
import playground.booookstore.query.type.OrderId
import playground.booookstore.query.usecase.OrderQueryUsecase

fun Route.queryOrderRouting() {
    route("/order") {
        get("{id}") {
            val orderIdParameter = call.parameters["id"] ?: return@get call.respondText(
                status = HttpStatusCode.BadRequest,
                text = "Missing id",
            )

            val orderId = try {
                OrderId.fromString(orderIdParameter)
            } catch (e: IllegalArgumentException) {
                return@get call.respondText(
                    status = HttpStatusCode.BadRequest,
                    text = "Illegal id"
                )
            }

            OrderQueryUsecase(
                OrderQueryGateway(RDBOrderDriver()),
                ShopQueryGateway(HttpClientShopDriver(HttpClient(CIO) {
                    install(ContentNegotiation) {
                        json()
                    }
                })),
                ItemQueryGateway()
            ).execute(orderId).let { call.respond(it) }
        }
    }
}
