package booookstore.playground.springmyplaygroundexposed.usecase

import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.UserRepository
import booookstore.playground.springmyplaygroundexposed.domain.MailAddress
import booookstore.playground.springmyplaygroundexposed.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserUsecase(private val userRepository: UserRepository) {

    @Transactional(readOnly = true)
    fun findUserByMailAddress(mailAddress: MailAddress): User {
        return userRepository.findByMailAddress(mailAddress)
    }

}
