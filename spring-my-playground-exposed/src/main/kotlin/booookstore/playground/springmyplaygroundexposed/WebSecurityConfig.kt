package booookstore.playground.springmyplaygroundexposed

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import java.util.function.Supplier

@Configuration
class WebSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.httpBasic(Customizer.withDefaults())
        http.csrf { it.disable() }
        http.authorizeHttpRequests {
            it
                .requestMatchers(GET, "/order/**").access(OrderAccessPolicy)
                .requestMatchers(POST, "/operation").hasAuthority("CREATE_OPERATION")
                .anyRequest().authenticated()

        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

}

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