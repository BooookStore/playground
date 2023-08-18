package playground.booookstore

import io.ktor.server.application.*
import playground.booookstore.plugins.configureRouting
import playground.booookstore.plugins.configureSerialization
import playground.booookstore.query.driver.dao.DatabaseFactory

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    configureRouting()
    configureSerialization()
}
