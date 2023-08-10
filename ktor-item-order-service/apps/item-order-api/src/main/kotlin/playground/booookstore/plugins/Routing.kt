package playground.booookstore.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import playground.booookstore.query.routes.queryOrderRouting
import playground.booookstore.routes.customerRouting

fun Application.configureRouting() {
    routing {
        customerRouting()
        queryOrderRouting()
    }
}
