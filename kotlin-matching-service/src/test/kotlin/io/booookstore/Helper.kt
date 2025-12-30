package io.booookstore

import io.booookstore.domain.EmailAddress
import io.booookstore.domain.User
import io.booookstore.domain.UserId
import java.util.UUID

fun createUser(): User {
    val uuid = UUID.randomUUID()
    return User(
        UserId(uuid),
        EmailAddress("$uuid@xxx")
    )
}
