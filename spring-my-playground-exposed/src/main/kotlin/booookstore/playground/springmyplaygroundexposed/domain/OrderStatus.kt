package booookstore.playground.springmyplaygroundexposed.domain

enum class OrderStatus {
    ACCEPTED,
    CANCELED;

    fun cancel() = when (this) {
        ACCEPTED -> CANCELED
        CANCELED -> throw IllegalStateException("already canceled order")
    }
}