package booookstore.playground.springmyplaygroundexposed.query

import arrow.core.Option
import java.util.*

interface OrderListViewQuery {

    fun searchByCreatedUserId(userId: UUID): List<OrderListView>

}