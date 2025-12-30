package io.booookstore.domain

data class PotentialCombinations(val values: Set<PotentialCombination>) {

    fun narrowDown(policy: NarrowDownPolicy) =
        values.map { PotentialCombination(it.listener, policy.apply(it.listener, it.speakers)) }
            .toSet()
            .let(::PotentialCombinations)

    companion object {

        fun of(vararg pairs: Pair<Listener, Speakers>) =
            pairs.map(PotentialCombination::of).toSet().let(::PotentialCombinations)

        fun matchMakeEvery(users: Users) =
            users.map {
                val listener = Listener(it)
                val speakers = users.map(::Speaker).toSet().let(::Speakers)
                PotentialCombination(listener, speakers)
            }.toSet().let(::PotentialCombinations)

    }

}

data class PotentialCombination(val listener: Listener, val speakers: Speakers) {

    companion object {

        fun of(pair: Pair<Listener, Speakers>) = PotentialCombination(pair.first, pair.second)

    }

}

@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
data class Speakers(val values: Set<Speaker>) : Set<Speaker> by values {

    companion object {

        fun of(vararg users: User): Speakers = Speakers(users.map(::Speaker).toSet())

    }

}

data class Speaker(val user: User)

data class Listener(val user: User)
