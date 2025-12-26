package io.booookstore.domain

interface NarrowDownPolicy {

    fun apply(listener: Listener, speakers: Speakers): Speakers

}

object Self : NarrowDownPolicy {

    override fun apply(listener: Listener, speakers: Speakers): Speakers =
        speakers.filterNot { speaker -> speaker.user == listener.user }.toSet().let(::Speakers)

}

class BeforeCombination(val combinationsHistory: CombinationsHistory) : NarrowDownPolicy {

    override fun apply(listener: Listener, speakers: Speakers) =
        speakers.filterNot { speaker -> combinationsHistory.isBeforeCombination(listener, speaker) }.toSet().let(::Speakers)

}