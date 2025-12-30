package io.booookstore.domain

import io.booookstore.createUser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PotentialCombinationsTest {

    @Test
    fun matchMakeEvery() {
        val userA = createUser()
        val userB = createUser()
        val users = Users.of(userA, userB)

        val actual = PotentialCombinations.matchMakeEvery(users)

        val expected = PotentialCombinations.of(
            Listener(userA) to Speakers.of(userA, userB),
            Listener(userB) to Speakers.of(userA, userB),
        )

        assertEquals(expected, actual)
    }

    @Test
    fun narrowDown() {
        val userA = createUser()
        val userB = createUser()

        val actual = PotentialCombinations.of(
            Listener(userA) to Speakers.of(userA, userB),
            Listener(userB) to Speakers.of(userA, userB),
        ).narrowDown(ExcludeSelf)

        val expected = PotentialCombinations.of(
            Listener(userA) to Speakers.of(userB),
            Listener(userB) to Speakers.of(userA),
        )

        assertEquals(expected, actual)
    }

}