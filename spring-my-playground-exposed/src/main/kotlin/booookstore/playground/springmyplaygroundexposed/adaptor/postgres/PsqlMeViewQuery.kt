package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import arrow.core.Option
import arrow.core.firstOrNone
import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.table.RoleTable
import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.table.SubjectTable
import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.table.UserTable
import booookstore.playground.springmyplaygroundexposed.query.MeView
import booookstore.playground.springmyplaygroundexposed.query.MeViewQuery
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PsqlMeViewQuery : MeViewQuery {
    override fun findMeById(userId: UUID): Option<MeView> =
        UserTable.select { UserTable.id eq userId }
            .limit(1)
            .map { MeView(it[UserTable.mailAddress], searchRoleNames(userId), emptySet(), emptySet()) }
            .firstOrNone()

    private fun searchRoleNames(userId: UUID): Set<String> =
        (RoleTable innerJoin SubjectTable)
            .select { SubjectTable.user eq userId }
            .map { it[RoleTable.name] }
            .toSet()
}