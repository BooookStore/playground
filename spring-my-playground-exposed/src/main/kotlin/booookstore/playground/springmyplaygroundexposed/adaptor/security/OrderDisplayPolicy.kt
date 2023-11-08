package booookstore.playground.springmyplaygroundexposed.adaptor.security

import arrow.core.None
import arrow.core.Some
import booookstore.playground.springmyplaygroundexposed.command.usecase.OrderUsecase
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class OrderDisplayPolicy(
    private val orderUsecase: OrderUsecase
) : AuthorizationManager<RequestAuthorizationContext> {

    override fun check(
        supplier: Supplier<Authentication>,
        context: RequestAuthorizationContext
    ): AuthorizationDecision = supplier.get().satisfy(
        { isAuthenticated },
        {
            hasAuthority("DISPLAY_ALL_ORDER") or
                    (ifHasAuthority("DISPLAY_CREATED_ORDER") satisfy { isCreatedOrder(context.variables["id"]) })
        }
    )

    private fun Authentication.satisfy(vararg conditions: (Authentication).() -> Boolean): AuthorizationDecision =
        conditions.map { it.invoke(this) }.all { it }.let(::AuthorizationDecision)

    private fun Authentication.isCreatedOrder(orderId: String?): Boolean = when (orderId) {
        null -> false
        else -> when (val order = orderUsecase.findById(orderId)) {
            is None -> false
            is Some -> order.value.createUser.toString() == name
        }
    }

    private fun Authentication.hasAuthority(authority: String) = authorities.map { it.authority }.contains(authority)

    private fun Authentication.ifHasAuthority(authority: String): SatisfyRule = if (hasAuthority(authority)) {
        MustSatisfyRule(this)
    } else {
        NoNeedSatisfyRule()
    }

    interface SatisfyRule {

        infix fun satisfy(condition: (Authentication) -> Boolean): Boolean

    }

    class MustSatisfyRule(private val authentication: Authentication) : SatisfyRule {

        override fun satisfy(condition: (Authentication) -> Boolean) = condition.invoke(authentication)

    }

    class NoNeedSatisfyRule : SatisfyRule {

        override fun satisfy(condition: (Authentication) -> Boolean): Boolean = true

    }

}