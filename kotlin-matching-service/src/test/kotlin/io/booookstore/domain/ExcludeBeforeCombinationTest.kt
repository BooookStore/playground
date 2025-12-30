package io.booookstore.domain

import io.booookstore.createUser
import org.junit.jupiter.api.Test
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

        val actual = policy.apply(Listener(userA), Speakers.of(userB, userC))
        val expected = Speakers.of(userC)

        assertEquals(expected, actual)
    }

}