package io.booookstore.port

import io.booookstore.domain.CombinationsHistory

interface CombinationsHistoryPort {

    fun find(): CombinationsHistory

}