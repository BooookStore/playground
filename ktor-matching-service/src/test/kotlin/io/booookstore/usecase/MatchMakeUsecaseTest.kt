package io.booookstore.usecase

import io.booookstore.createUser
import io.booookstore.domain.Listener
import io.booookstore.domain.PotentialCombination.Companion.with
import io.booookstore.domain.PotentialCombinations
import io.booookstore.domain.Speakers
import io.booookstore.domain.User
import io.booookstore.domain.Users
import io.booookstore.port.UsersPort
import kotlin.test.Test
import kotlin.test.assertEquals

class MatchMakeUsecaseTest {

    @Test
    fun execute() {
        val userA = createUser()
        val userB = createUser()
        UsersGatewayMock.hold(userA, userB)

        val actual = MatchMakeUsecase(UsersGatewayMock).execute()

        val expected = PotentialCombinations.of(
            Listener(userA) with Speakers.of(userB),
            Listener(userB) with Speakers.of(userA),
        )

        assertEquals(expected, actual)
    }

    object UsersGatewayMock : UsersPort {
        
        private var users: Users? = null

        override fun findUsers(): Users = users ?: throw Exception("Users not initialized")
      
        fun hold(vararg user: User) {
            users = Users.of(*user)
        }

    }

}