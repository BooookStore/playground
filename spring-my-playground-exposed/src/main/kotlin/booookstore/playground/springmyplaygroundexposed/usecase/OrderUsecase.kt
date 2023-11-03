package booookstore.playground.springmyplaygroundexposed.usecase

import arrow.core.Option
import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.OrderRepository
import booookstore.playground.springmyplaygroundexposed.domain.MailAddress
import booookstore.playground.springmyplaygroundexposed.domain.Order
import booookstore.playground.springmyplaygroundexposed.domain.OrderId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderUsecase(val orderRepository: OrderRepository, val userUsecase: UserUsecase) {

    @Transactional(readOnly = true)
    fun findById(orderId: OrderId): Option<Order> {
        return orderRepository.findById(orderId)
    }

    @Transactional
    fun createOrder(order: Order, userMailAddress: MailAddress) {
        val user = userUsecase.findUserByMailAddress(userMailAddress)
        orderRepository.saveAsNew(order, user)
    }

}