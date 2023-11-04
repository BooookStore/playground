package booookstore.playground.springmyplaygroundexposed.domain

import booookstore.playground.springmyplaygroundexposed.domain.OrderStatus.ACCEPTED

typealias OrderId = String

data class Order(val id: String, val name: String, var status: OrderStatus) {

    companion object {

        fun acceptNewOrder(id: String, name: String) = Order(id, name, ACCEPTED)

    }

    fun cancel() = status.cancel().also { status = it }

}