package booookstore.playground.springmyplaygroundexposed.domain

typealias OperationId = String

data class Operation(
    val id: OperationId,
    val name: String
)