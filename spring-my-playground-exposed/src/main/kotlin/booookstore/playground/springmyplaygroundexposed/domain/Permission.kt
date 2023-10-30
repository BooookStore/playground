package booookstore.playground.springmyplaygroundexposed.domain

typealias PermissionId = String

data class Permission(
    val id: PermissionId,
    val name: String,
    val operations: Set<OperationId>
)