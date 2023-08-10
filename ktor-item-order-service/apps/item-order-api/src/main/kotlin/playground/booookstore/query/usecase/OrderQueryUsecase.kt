package playground.booookstore.query.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import playground.booookstore.query.repository.ItemQueryRepository
import playground.booookstore.query.repository.OrderQueryRepository
import playground.booookstore.query.type.ItemId
import playground.booookstore.query.type.OrderId

class OrderQueryUsecase(
    private val orderQueryRepository: OrderQueryRepository,
    private val itemQueryRepository: ItemQueryRepository,
) {

    suspend fun execute(orderId: OrderId) = coroutineScope {
        val orderDeferred = async { orderQueryRepository.findOrder(orderId) }
        val itemsDeferred = async { itemQueryRepository.find(orderId) }

        val order = orderDeferred.await()
        val items = itemsDeferred.await()

        OrderQueryView(
            id = order.id,
            orderDateTime = order.orderDateTime,
            items = items.set.map { OrderQueryViewItem(id = it.id, name = it.name) }.toMutableList()
        )
    }

}

@Serializable
data class OrderQueryView(
    val id: OrderId,
    val orderDateTime: LocalDateTime,
    val items: MutableList<OrderQueryViewItem>,
)

@Serializable
data class OrderQueryViewItem(
    val id: ItemId,
    val name: String
)
