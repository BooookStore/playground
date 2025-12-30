package io.booookstore.domain

import io.booookstore.createUser
import io.booookstore.speakersOf
import kotlin.test.Test
import kotlin.test.assertEquals

class ExcludeBeforeCombinationTest {

    @Test
    fun applyBeforeCombination() {
        val userA = createUser()
        val userB = createUser()
        val userC = createUser()

        val policy = ExcludeBeforeCombination(CombinationsHistory.of(
            Listener(userA) to Speaker(userB),
        ))

        val actual = policy.apply(Listener(userA), speakersOf(userB, userC))
        val expected = speakersOf(userC)

        assertEquals(expected, actual)
    }

}