package io.booookstore.domain

import io.booookstore.createUser
import io.booookstore.domain.CombinationHistory.Companion.with
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class ExcludeBeforeCombinationPolicyTest {

    val userA = createUser()
    val userB = createUser()
    val userC = createUser()

    lateinit var beforeCombination: BeforeCombination

    @BeforeEach
    fun setCombinationsHistory() {
        beforeCombination = BeforeCombination(CombinationsHistory.of(
            Listener(userA) with Speaker(userB),
        ))
    }

    @Test
    fun excludeBeforeCombination() {
        val actual = beforeCombination.exclude(Listener(userA), Speakers.of(userB, userC))
        val expected = Speakers.of(userC)
        assertEquals(expected, actual)
    }

}