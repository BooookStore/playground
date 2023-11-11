package booookstore.playground.springmyplaygroundexposed.query

data class MeView(
    val mailAddress: String,
    val roleNames: Set<String>,
    val permissionNames: Set<String>,
    val operations: Set<String>,
)