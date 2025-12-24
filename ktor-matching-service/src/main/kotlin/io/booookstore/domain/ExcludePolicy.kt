package io.booookstore.domain

interface ExcludePolicy {

    fun exclude(listener: Listener, speakers: Speakers): Speakers

}

object Self : ExcludePolicy {

    override fun exclude(listener: Listener, speakers: Speakers): Speakers =
        speakers.filterNot { speaker -> speaker.user == listener.user }.toSet().let(::Speakers)

}

class BeforeCombination(val combinationsHistory: CombinationsHistory) : ExcludePolicy {

    override fun exclude(listener: Listener, speakers: Speakers) =
        speakers.filterNot { speaker -> combinationsHistory.isBeforeCombination(listener, speaker) }.toSet().let(::Speakers)

}