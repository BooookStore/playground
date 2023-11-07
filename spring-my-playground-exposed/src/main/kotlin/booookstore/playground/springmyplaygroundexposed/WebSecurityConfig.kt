@file:Suppress("DEPRECATION")

package booookstore.playground.springmyplaygroundexposed

import booookstore.playground.springmyplaygroundexposed.adaptor.security.OrderDisplayPolicy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class WebSecurityConfig(
    private val orderDisplayPolicy: OrderDisplayPolicy
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.httpBasic(Customizer.withDefaults())
        http.csrf { it.disable() }
        http.authorizeHttpRequests {
            it
                .requestMatchers(GET, "/order/{id}").access(orderDisplayPolicy)
                .requestMatchers(POST, "/order").hasAuthority("CREATE_ORDER")
                .requestMatchers(PUT, "/order/*/cancel").hasAuthority("CANCEL_ALL_ORDER")
                .anyRequest().permitAll()

        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

}
