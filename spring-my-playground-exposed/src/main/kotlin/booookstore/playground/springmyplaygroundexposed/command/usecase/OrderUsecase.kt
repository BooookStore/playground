package booookstore.playground.springmyplaygroundexposed.command.usecase

import arrow.core.Option
import booookstore.playground.springmyplaygroundexposed.command.domain.order.OrderRepository
import booookstore.playground.springmyplaygroundexposed.command.domain.order.Order
import booookstore.playground.springmyplaygroundexposed.command.domain.order.OrderId
import booookstore.playground.springmyplaygroundexposed.command.domain.user.UserId
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