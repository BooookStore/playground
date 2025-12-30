package io.booookstore.domain

import io.booookstore.createUser
import io.booookstore.speakersOf
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
            Listener(userA) to speakersOf(userA, userB),
            Listener(userB) to speakersOf(userA, userB),
        )

        assertEquals(expected, actual)
    }

    @Test
    fun narrowDown() {
        val userA = createUser()
        val userB = createUser()

        val actual = PotentialCombinations.of(
            Listener(userA) to speakersOf(userA, userB),
            Listener(userB) to speakersOf(userA, userB),
        ).narrowDown(ExcludeSelf)

        val expected = PotentialCombinations.of(
            Listener(userA) to speakersOf(userB),
            Listener(userB) to speakersOf(userA),
        )

        assertEquals(expected, actual)
    }

}