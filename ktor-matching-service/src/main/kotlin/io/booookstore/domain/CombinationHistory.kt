package io.booookstore.domain

data class CombinationsHistory(val values: Set<CombinationHistory>) {

    fun isBeforeCombination(listener: Listener, speaker: Speaker) =
        values.any { history -> history.listener == listener && history.speaker == speaker }

    companion object {

        fun of(vararg pairs: Pair<Listener, Speaker>) =
            CombinationsHistory(pairs.map { CombinationHistory(it.first, it.second) }.toSet())

    }

}

data class CombinationHistory(val listener: Listener, val speaker: Speaker)