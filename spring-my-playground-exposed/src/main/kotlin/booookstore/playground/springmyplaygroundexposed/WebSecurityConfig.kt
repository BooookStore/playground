@file:Suppress("DEPRECATION")

package booookstore.playground.springmyplaygroundexposed

import booookstore.playground.springmyplaygroundexposed.adaptor.security.OrderDisplayPolicy
import booookstore.playground.springmyplaygroundexposed.security.JsonUsernamePasswordAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.context.HttpSessionSecurityContextRepository

@Configuration
class WebSecurityConfig(
    private val orderDisplayPolicy: OrderDisplayPolicy
) {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jsonUsernamePasswordAuthenticationFilter: JsonUsernamePasswordAuthenticationFilter,
    ): SecurityFilterChain {
        http {
            addFilterAt(jsonUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

            csrf { disable() }
            cors { }

            authorizeHttpRequests {
                authorize("/order/{id}", access = orderDisplayPolicy)
                authorize("/order", hasAuthority("CREATE_ORDER"))
                authorize("/oder/*/cancel", hasAuthority("CANCEL_ALL_ORDER"))
                authorize(anyRequest, authenticated)
            }

            logout {
                logoutSuccessHandler =
                    LogoutSuccessHandler { _, response, _ -> response.status = HttpStatus.OK.value() }
            }
        }
        return http.build()
    }

    @Bean
    fun jsonUsernamePasswordAuthenticationFilter(providerManager: ProviderManager): JsonUsernamePasswordAuthenticationFilter {
        return JsonUsernamePasswordAuthenticationFilter(providerManager).apply {
            setSecurityContextRepository(HttpSessionSecurityContextRepository())
            setAuthenticationSuccessHandler { _, response, _ -> response.status = HttpStatus.OK.value() }
            setAuthenticationFailureHandler { _, response, _ -> response.status = HttpStatus.UNAUTHORIZED.value() }
        }
    }

    @Bean
    fun providerManager(userDetailsService: UserDetailsService, passwordEncoder: PasswordEncoder): ProviderManager {
        return DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder)
        }.let { ProviderManager(it) }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

}
