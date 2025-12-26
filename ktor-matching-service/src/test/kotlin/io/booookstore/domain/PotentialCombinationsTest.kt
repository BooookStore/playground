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
        val potentialCombinations = PotentialCombinations.matchMakeEvery(users)

        val expected = PotentialCombinations.of(
            PotentialCombination(Listener(userA), Speakers.of(userA, userB)),
            PotentialCombination(Listener(userB), Speakers.of(userA, userB)),
        )

        assertEquals(expected, potentialCombinations)
    }

    @Test
    fun narrowDown() {
        val userA = createUser()
        val userB = createUser()

        val potentialCombinations = PotentialCombinations.of(
            Listener(userA) with Speakers.of(userA, userB),
            Listener(userB) with Speakers.of(userA, userB),
        ).narrowDown(Self)

        val expected = PotentialCombinations.of(
            PotentialCombination(Listener(userA), Speakers.of(userB)),
            PotentialCombination(Listener(userB), Speakers.of(userA)),
        )

        assertEquals(expected, potentialCombinations)
    }

}