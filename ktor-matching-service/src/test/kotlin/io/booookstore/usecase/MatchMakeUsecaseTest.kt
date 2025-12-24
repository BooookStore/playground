package io.booookstore.usecase

import io.booookstore.createUser
import io.booookstore.domain.*
import io.booookstore.domain.PotentialCombination.Companion.with
import io.booookstore.port.UsersPort
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MatchMakeUsecaseTest {

    val userA = createUser()
    val userB = createUser()

    @BeforeEach
    fun setupUsersGatewayMock() {
        UsersGatewayMock.hold(userA, userB)
    }

    @Test
    fun execute() {
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