package playground.booookstore.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
            Mutex().withLock {
                customerStorage.add(customer)
            }
            call.respondText("Customer storage correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            Mutex().withLock {
                if (customerStorage.removeIf { it.id == id }) {
                    call.respondText("Customer remove correctly", status = HttpStatusCode.Accepted)
                } else {
                    call.respondText("Not Found", status = HttpStatusCode.NotFound)
                }
            }
        }
    }
}

