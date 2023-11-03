package booookstore.playground.springmyplaygroundexposed

import booookstore.playground.springmyplaygroundexposed.adaptor.security.OrderAccessPolicy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

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
