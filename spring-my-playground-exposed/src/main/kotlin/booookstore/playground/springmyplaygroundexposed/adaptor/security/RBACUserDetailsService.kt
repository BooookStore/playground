package booookstore.playground.springmyplaygroundexposed.adaptor.security

import booookstore.playground.springmyplaygroundexposed.command.usecase.UserUsecase
import booookstore.playground.springmyplaygroundexposed.command.usecase.OperationUsecase
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class RBACUserDetailsService(
    val userUsecase: UserUsecase,
    val operationUsecase: OperationUsecase,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userUsecase.findUserByMailAddress(username)
        val operations = operationUsecase.findOperationByUserMailAddress(username)
        return User.withUsername(user.id.toString())
            .password(user.password)
            .authorities(operations.map { SimpleGrantedAuthority(it.name) })
            .build()
    }

}