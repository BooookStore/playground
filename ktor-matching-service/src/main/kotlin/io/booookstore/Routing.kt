package io.booookstore

import io.booookstore.handler.createPotentialCombination
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/systems/ping") {
            call.respondText("pong")
        }
        post("/potentialCombination", ::createPotentialCombination)
    }
}