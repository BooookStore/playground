package io.booookstore.domain

interface ExcludePolicy {

    fun exclude(listener: Listener, speakers: Speakers): Speakers

}

object Self : ExcludePolicy {

    override fun exclude(listener: Listener, speakers: Speakers): Speakers =
        speakers.values.filterNot { speaker -> speaker.user == listener.user }.toSet().let(::Speakers)

}