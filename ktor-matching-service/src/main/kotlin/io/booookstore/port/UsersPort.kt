package io.booookstore.port

import io.booookstore.domain.Users

interface UsersPort {

    fun findUsers(): Users

}