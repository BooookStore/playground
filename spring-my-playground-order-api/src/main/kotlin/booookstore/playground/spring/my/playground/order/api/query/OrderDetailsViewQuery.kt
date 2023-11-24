package booookstore.playground.spring.my.playground.order.api.query

import arrow.core.Option

interface OrderDetailsViewQuery {
    fun searchById(orderId: String): Option<OrderDetailsView>
}