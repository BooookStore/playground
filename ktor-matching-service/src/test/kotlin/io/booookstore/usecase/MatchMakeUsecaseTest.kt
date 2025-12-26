package io.booookstore.usecase

import io.booookstore.createUser
import io.booookstore.domain.*
import io.booookstore.domain.PotentialCombination.Companion.with
import io.booookstore.port.CombinationsHistoryPort
import io.booookstore.port.UsersPort
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MatchMakeUsecaseTest {

    val userA = createUser()
    val userB = createUser()
    val userC = createUser()

    @BeforeEach
    fun setupUsersGatewayMock() {
        UsersGatewayMock.hold(userA, userB, userC)
        CombinationsHistoryGatewayMock.hold(CombinationHistory(Listener(userA), Speaker(userC)))
    }

    @AfterEach
    fun tearDownUsersGatewayMock() {
        UsersGatewayMock.clearHolds()
        CombinationsHistoryGatewayMock.clearHolds()
    }

    @Test
    fun execute() {
        val actual = MatchMakeUsecase(UsersGatewayMock, CombinationsHistoryGatewayMock).execute()

        val expected = PotentialCombinations.of(
            Listener(userA) with Speakers.of(userB),
            Listener(userB) with Speakers.of(userA, userC),
            Listener(userC) with Speakers.of(userB, userA),
        )

        assertEquals(expected, actual)
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

    object CombinationsHistoryGatewayMock : CombinationsHistoryPort {

        private var combinationsHistory: CombinationsHistory? = null

        override fun find(): CombinationsHistory = combinationsHistory ?: throw Exception("CombinationsHistory not found")

        fun hold(vararg combinationHistory: CombinationHistory) {
            combinationsHistory = CombinationsHistory.of(*combinationHistory)
        }

        fun clearHolds() {
            combinationsHistory = null
        }

    }

}