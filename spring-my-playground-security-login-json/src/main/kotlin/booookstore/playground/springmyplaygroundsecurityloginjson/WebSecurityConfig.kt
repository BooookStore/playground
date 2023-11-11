package booookstore.playground.springmyplaygroundsecurityloginjson

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.DelegatingSecurityContextRepository
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
class WebSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        http.csrf { it.disable() }
        http.cors {
//            it.configurationSource(CorsConfigurationSource {
//                val configuration = CorsConfiguration()
//                configuration.allowedOrigins = listOf("*")
//                configuration.allowedMethods = listOf("POST")
//            })
        }
        http.authorizeHttpRequests {
            it
                .anyRequest().authenticated()
        }
        val filter = JsonLoginAuthenticationFilter(authenticationManager).apply {
            setSecurityContextRepository(
                DelegatingSecurityContextRepository(
                    HttpSessionSecurityContextRepository(),
                    RequestAttributeSecurityContextRepository()
                )
            )
        }
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun authenticationManager(
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder
    ): ProviderManager {
        val authenticationProvider = DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder)
        }
        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return InMemoryUserDetailsManager().apply {
            createUser(User.withUsername("bookstore1@playground").password("password").build())
        }
    }

    @Suppress("DEPRECATION")
    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

}