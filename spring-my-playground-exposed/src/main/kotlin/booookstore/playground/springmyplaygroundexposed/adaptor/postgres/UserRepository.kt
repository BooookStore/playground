package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.table.UserTable
import booookstore.playground.springmyplaygroundexposed.command.domain.user.MailAddress
import booookstore.playground.springmyplaygroundexposed.command.domain.user.User
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

    fun findByMailAddress(mailAddress: MailAddress) =
        UserTable.select { UserTable.mailAddress eq mailAddress }
            .map {
                User(
                    it[UserTable.id],
                    it[UserTable.mailAddress],
                    it[UserTable.password]
                )
            }
            .first()

}
