package playground.booookstore.domain

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

val customerStorage = mutableListOf(
    Customer(
        id = UUID.randomUUID().toString(),
        firstName = "Book",
        lastName = "Store",
        email = "sleep@booookstore.playground"
    )
)