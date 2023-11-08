package booookstore.playground.springmyplaygroundexposed.query

import arrow.core.Option

interface OrderDetailsViewQuery {
    fun searchById(orderId: String): Option<OrderDetailsView>
}