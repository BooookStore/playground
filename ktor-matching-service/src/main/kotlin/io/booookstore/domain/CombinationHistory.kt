package io.booookstore.domain

data class CombinationsHistory(val values: Set<CombinationHistory>) {

    fun isBeforeCombination(listener: Listener, speaker: Speaker) =
        values.any { history -> history.listener == listener && history.speaker == speaker }

    companion object {

        fun of(vararg values: CombinationHistory) = CombinationsHistory(values.toSet())

    }

}

data class CombinationHistory(val listener: Listener, val speaker: Speaker) {

    companion object {

        infix fun Listener.with(speaker: Speaker) = CombinationHistory(this, speaker)

    }

}