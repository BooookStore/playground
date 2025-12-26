package io.booookstore.domain

import io.booookstore.createUser
import io.booookstore.domain.PotentialCombination.Companion.with
import kotlin.test.Test
import kotlin.test.assertEquals

class PotentialCombinationsTest {

    @Test
    fun matchMakeEvery() {
        val userA = createUser()
        val userB = createUser()

        val users = Users.of(userA, userB)
        val actual = PotentialCombinations.matchMakeEvery(users)

        val expected = PotentialCombinations.of(
            Listener(userA) with Speakers.of(userA, userB),
            Listener(userB) with Speakers.of(userA, userB),
        )

        assertEquals(expected, actual)
    }

    @Test
    fun narrowDown() {
        val userA = createUser()
        val userB = createUser()

        val actual = PotentialCombinations.of(
            Listener(userA) with Speakers.of(userA, userB),
            Listener(userB) with Speakers.of(userA, userB),
        ).narrowDown(Self)

        val expected = PotentialCombinations.of(
            Listener(userA) with Speakers.of(userB),
            Listener(userB) with Speakers.of(userA),
        )

        assertEquals(expected, actual)
    }

}