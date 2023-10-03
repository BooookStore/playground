package playground.booookstore.query.port

import playground.booookstore.query.type.ShopId

interface ShopQueryPort {

    suspend fun find(shopId: ShopId): ShopQueryModel

}

data class ShopQueryModel(val id: ShopId, val name: String)