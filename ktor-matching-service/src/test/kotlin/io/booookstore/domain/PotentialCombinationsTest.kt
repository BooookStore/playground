package io.booookstore.domain

import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PotentialCombinationsTest {

    @Test
    fun every() {
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
    fun excludeSpeakers() {
        val userA = createUser()
        val userB = createUser()

        val users = Users.of(userA, userB)
        val potentialCombinations = PotentialCombinations
            .matchMakeEvery(users)
            .excludeSpeakers(Self)

        val expected = PotentialCombinations.of(
            PotentialCombination(Listener(userA), Speakers.of(userB)),
            PotentialCombination(Listener(userB), Speakers.of(userA)),
        )

        assertEquals(expected, potentialCombinations)
    }

    fun createUser(): User {
        val uuid = UUID.randomUUID()
        return User(
            UserId(uuid),
            EmailAddress("$uuid@xxx")
        )
    }
}