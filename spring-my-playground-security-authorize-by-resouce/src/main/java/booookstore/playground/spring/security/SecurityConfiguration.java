package booookstore.playground.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain configure(HttpSecurity http, ProtectedResourcePolicy protectedResourcePolicy) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(c -> c
                .requestMatchers(antMatcher("/resource/{id}"))
                .access(protectedResourcePolicy::isPermitted)
                .anyRequest().authenticated());
        return http.build();
    }

}
