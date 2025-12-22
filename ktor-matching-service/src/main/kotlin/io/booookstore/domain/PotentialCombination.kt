package io.booookstore.domain

data class PotentialCombinations(val values: Set<PotentialCombination>) {

    fun excludeSpeakers(excludePolicy: ExcludePolicy) =
        values.map {
            PotentialCombination(it.listener, excludePolicy.exclude(it.listener, it.speakers))
        }.toSet().let(::PotentialCombinations)

    companion object {

        fun of(vararg potentialCombinations: PotentialCombination) =
            PotentialCombinations(potentialCombinations.toSet())

        fun matchMakeEvery(users: Users) =
            users.map {
                val listener = Listener(it)
                val speakers = users.map(::Speaker).toSet().let(::Speakers)
                PotentialCombination(listener, speakers)
            }.toSet().let(::PotentialCombinations)

    }

}

data class PotentialCombination(val listener: Listener, val speakers: Speakers)

@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
data class Speakers(val values: Set<Speaker>) : Set<Speaker> by values {

    companion object {

        fun of(vararg users: User): Speakers = Speakers(users.map(::Speaker).toSet())

    }

}

data class Speaker(val user: User)

data class Listener(val user: User)
