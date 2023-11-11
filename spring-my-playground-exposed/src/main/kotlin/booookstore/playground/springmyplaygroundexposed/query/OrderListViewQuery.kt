package booookstore.playground.springmyplaygroundexposed.query

import java.util.*

interface OrderListViewQuery {

    fun searchByCreatedUserId(userId: UUID)

}