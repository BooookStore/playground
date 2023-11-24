package booookstore.playground.spring.my.playground.order.api.query

import arrow.core.Option
import java.util.*

interface OrderListViewQuery {

    fun searchByCreatedUserId(userId: UUID): List<OrderListView>

}