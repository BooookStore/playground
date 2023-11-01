package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import booookstore.playground.springmyplaygroundexposed.domain.MailAddress
import booookstore.playground.springmyplaygroundexposed.domain.User
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

    fun findByMailAddress(mailAddress: MailAddress) =
        UserTable.select { UserTable.mailAddress eq mailAddress }
            .map { User(it[UserTable.mailAddress], it[UserTable.password]) }
            .first()

}
