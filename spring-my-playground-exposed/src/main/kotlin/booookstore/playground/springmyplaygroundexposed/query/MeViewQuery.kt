package booookstore.playground.springmyplaygroundexposed.query

import arrow.core.Option
import java.util.UUID

interface MeViewQuery {
    fun findMeById(userId: UUID): Option<MeView>
}