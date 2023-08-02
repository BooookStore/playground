package playground.booookstore.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import playground.booookstore.models.Customer
import playground.booookstore.models.customerStorage

fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty()) {
                call.respond<List<Customer>>(customerStorage)
            } else {
                call.respondText("No customers found")
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest,
            )
            val customer = customerStorage.find { it.id == id } ?: return@get call.respondText(
                "No customer with id $id",
                status = HttpStatusCode.NotFound,
            )
            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            // FIX ME: 複数のリクエストが同時に発生した場合、複数のスレッドが変数へアクセスすることを考慮すること。
            customerStorage.add(customer)
            call.respondText("Customer storage correctly", status = HttpStatusCode.Created)
        }
        delete {
        }
    }
}

