package io.booookstore.domain

import java.util.*

@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
data class Users(val values: Set<User>) : Set<User> by values {

    companion object {

        fun of(vararg users: User): Users = Users(users.toSet())

    }

}

data class User(val userId: UserId, val emailAddress: EmailAddress)

data class UserId(val value: UUID)

data class EmailAddress(val value: String)