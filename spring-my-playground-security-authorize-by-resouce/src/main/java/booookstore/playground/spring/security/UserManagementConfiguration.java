package booookstore.playground.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserManagementConfiguration {

    @SuppressWarnings("deprecation")
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    UserDetailsService userDetailsService() {
        var user1 = User.withUsername("bookstore1")
                .password("password")
                .authorities("read")
                .build();
        var user2 = User.withUsername("bookstore2")
                .password("password")
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

}
