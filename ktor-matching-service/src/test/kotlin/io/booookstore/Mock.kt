package io.booookstore

import io.booookstore.domain.CombinationHistory
import io.booookstore.domain.CombinationsHistory
import io.booookstore.domain.Listener
import io.booookstore.domain.Speaker
import io.booookstore.domain.User
import io.booookstore.domain.Users
import io.booookstore.port.CombinationsHistoryPort
import io.booookstore.port.UsersPort

object CombinationsHistoryGatewayMock : CombinationsHistoryPort {

    private var combinationsHistory: CombinationsHistory? = null

    override fun find(): CombinationsHistory = combinationsHistory ?: throw Exception("CombinationsHistory not found")

    fun hold(vararg pairs: Pair<Listener, Speaker>) {
        val values = pairs.map { CombinationHistory(it.first, it.second) }.toTypedArray()
        combinationsHistory = CombinationsHistory.of(*values)
    }

    fun clearHolds() {
        combinationsHistory = null
    }

}

object UsersGatewayMock : UsersPort {

    private var users: Users? = null

    override fun findUsers(): Users = users ?: throw Exception("Users not initialized")

    fun hold(vararg user: User) {
        users = Users.of(*user)
    }

    fun clearHolds() {
        users = null
    }

}