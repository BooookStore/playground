package io.booookstore.usecase

import io.booookstore.CombinationsHistoryGatewayMock
import io.booookstore.UsersGatewayMock
import io.booookstore.createUser
import io.booookstore.domain.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MatchMakeUsecaseTest {

    val userA = createUser()
    val userB = createUser()
    val userC = createUser()

    @BeforeEach
    fun setupUsersGatewayMock() {
        UsersGatewayMock.hold(userA, userB, userC)
        CombinationsHistoryGatewayMock.hold(Listener(userA) to Speaker(userC))
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
            Listener(userA) to Speakers.of(userB),
            Listener(userB) to Speakers.of(userA, userC),
            Listener(userC) to Speakers.of(userB, userA),
        )

        assertEquals(expected, actual)
    }

}