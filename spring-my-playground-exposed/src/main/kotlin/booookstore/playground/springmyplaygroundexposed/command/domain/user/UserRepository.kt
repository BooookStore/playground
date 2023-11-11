package booookstore.playground.springmyplaygroundexposed.command.domain.user

interface UserRepository {
    fun findByMailAddress(mailAddress: MailAddress): User
}