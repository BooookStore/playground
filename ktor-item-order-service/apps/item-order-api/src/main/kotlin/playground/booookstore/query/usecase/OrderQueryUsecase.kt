package playground.booookstore.query.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory
import playground.booookstore.query.port.ItemQueryPort
import playground.booookstore.query.port.OrderQueryPort
import playground.booookstore.query.port.ShopQueryPort
import playground.booookstore.query.type.ItemId
import playground.booookstore.query.type.OrderId
import playground.booookstore.query.type.ShopId

class OrderQueryUsecase(
    private val orderQueryPort: OrderQueryPort,
    private val shopQueryPort: ShopQueryPort,
    private val itemQueryPort: ItemQueryPort,
) {

    private val logger = LoggerFactory.getLogger(OrderQueryUsecase::class.java)

    suspend fun execute(orderId: OrderId) = coroutineScope {
        logger.info("execute order query by orderId {}", orderId)
        val orderDeferred = async { orderQueryPort.find(orderId) }
        val itemsDeferred = async { itemQueryPort.find(orderId) }

        val order = orderDeferred.await()

        val shopDeferred = async { shopQueryPort.find(order.shopId) }

        val shop = shopDeferred.await()
        val items = itemsDeferred.await()

        OrderQueryView(
            id = order.id.toString(),
            orderDateTime = order.orderDateTime,
            shop = ShopQueryView(id = shop.id, name = shop.name),
            items = items.set.map { OrderQueryViewItem(id = it.id, name = it.name) }.toMutableList()
        )
    }

}

@Serializable
data class OrderQueryView(
    val id: String,
    val orderDateTime: LocalDateTime,
    val shop: ShopQueryView,
    val items: MutableList<OrderQueryViewItem>,
)

@Serializable
data class ShopQueryView(
    val id: ShopId,
    val name: String,
)

@Serializable
data class OrderQueryViewItem(
    val id: ItemId,
    val name: String
)
