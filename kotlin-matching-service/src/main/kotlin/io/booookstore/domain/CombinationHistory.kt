package io.booookstore.domain

data class CombinationsHistory(val values: Set<CombinationHistory>) {

    fun isBeforeCombination(listener: Listener, speaker: Speaker) =
        values.any { history -> history.listener == listener && history.speaker == speaker }

    companion object {

        fun of(vararg pairs: Pair<Listener, Speaker>) =
            pairs.map(CombinationHistory::of)
                .toSet()
                .let(::CombinationsHistory)

    }

}

data class CombinationHistory(val listener: Listener, val speaker: Speaker) {

    companion object {

        fun of(pair: Pair<Listener, Speaker>) = CombinationHistory(pair.first, pair.second)

    }

}