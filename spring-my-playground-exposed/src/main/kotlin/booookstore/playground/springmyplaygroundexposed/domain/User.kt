package booookstore.playground.springmyplaygroundexposed.domain

import java.util.*

typealias UserId = UUID

typealias MailAddress = String

data class User(val id: UserId, val mailAddress: MailAddress, val password: String)