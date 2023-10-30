package booookstore.playground.springmyplaygroundexposed.domain

typealias RoleId = String

data class Role(
    val id: RoleId,
    val name: String,
    val permissions: Set<PermissionId>
)
