package booookstore.playground.springmyplaygroundexposed.command.domain.user

typealias PermissionId = String

data class Permission(
    val id: PermissionId,
    val name: String,
    val operations: Set<OperationId>
)