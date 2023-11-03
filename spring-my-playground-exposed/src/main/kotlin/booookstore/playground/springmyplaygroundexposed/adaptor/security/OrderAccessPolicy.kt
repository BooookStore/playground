package booookstore.playground.springmyplaygroundexposed.adaptor.security

import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import java.util.function.Supplier

object OrderAccessPolicy : AuthorizationManager<RequestAuthorizationContext> {

    override fun check(
        supplier: Supplier<Authentication>,
        `object`: RequestAuthorizationContext
    ): AuthorizationDecision = supplier.get().satisfy(
        { isAuthenticated },
        { hasAuthority("DISPLAY_ALL_ORDER") }
    )

    private fun Authentication.satisfy(vararg conditions: (Authentication).() -> Boolean): AuthorizationDecision =
        conditions.map { it.invoke(this) }.all { it }.let(::AuthorizationDecision)

    private fun Authentication.hasAuthority(authority: String) = authorities.map { it.authority }.contains(authority)

}