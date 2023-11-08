package booookstore.playground.springmyplaygroundexposed.command.domain.user

typealias RoleId = String

data class Role(
    val id: RoleId,
    val name: String,
    val permissions: Set<PermissionId>
)
