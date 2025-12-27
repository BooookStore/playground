package io.booookstore.domain

import io.booookstore.createUser
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class NarrowDownBeforeCombinationPolicyTest {

    val userA = createUser()
    val userB = createUser()
    val userC = createUser()

    lateinit var beforeCombination: BeforeCombination

    @BeforeEach
    fun setCombinationsHistory() {
        beforeCombination = BeforeCombination(CombinationsHistory.of(
            Listener(userA) to Speaker(userB),
        ))
    }

    @Test
    fun applyBeforeCombination() {
        val actual = beforeCombination.apply(Listener(userA), Speakers.of(userB, userC))
        val expected = Speakers.of(userC)
        assertEquals(expected, actual)
    }

}