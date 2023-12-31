package booookstore.playground.springmyplaygroundexposed.command.usecase

import booookstore.playground.springmyplaygroundexposed.command.domain.user.MailAddress
import booookstore.playground.springmyplaygroundexposed.command.domain.user.User
import booookstore.playground.springmyplaygroundexposed.command.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserUsecase(private val userRepository: UserRepository) {

    @Transactional(readOnly = true)
    fun findUserByMailAddress(mailAddress: MailAddress): User {
        return userRepository.findByMailAddress(mailAddress)
    }

}
