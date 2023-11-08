package booookstore.playground.springmyplaygroundexposed.command.domain.user

typealias OperationId = String

data class Operation(
    val id: OperationId,
    val name: String
)