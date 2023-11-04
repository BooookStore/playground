package booookstore.playground.springmyplaygroundexposed.usecase

import arrow.core.Option
import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.OrderRepository
import booookstore.playground.springmyplaygroundexposed.domain.Order
import booookstore.playground.springmyplaygroundexposed.domain.OrderId
import booookstore.playground.springmyplaygroundexposed.domain.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderUsecase(val orderRepository: OrderRepository) {

    @Transactional(readOnly = true)
    fun findById(orderId: OrderId): Option<Order> {
        return orderRepository.findById(orderId)
    }

    @Transactional
    fun acceptOrder(order: Order, userId: UserId) {
        orderRepository.saveAsNew(order, userId)
    }

    @Transactional
    fun cancelOrder(orderId: OrderId, cancelUserId: UserId) = orderRepository.findById(orderId)
        .onSome { order ->
            order.cancel(cancelUserId)
            orderRepository.saveAsOverride(order)
        }
        .onNone { throw Exception("order can't cancel. order not found $orderId") }

}