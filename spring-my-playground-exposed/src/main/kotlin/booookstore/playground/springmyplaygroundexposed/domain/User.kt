package booookstore.playground.springmyplaygroundexposed.domain

typealias MailAddress = String

data class User(val mailAddress: MailAddress, val password: String)