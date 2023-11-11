package booookstore.playground.springmyplaygroundexposed.adaptor.postgres

import arrow.core.Option
import arrow.core.firstOrNone
import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.table.*
import booookstore.playground.springmyplaygroundexposed.query.MeView
import booookstore.playground.springmyplaygroundexposed.query.MeViewQuery
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PsqlMeViewQuery : MeViewQuery {
    override fun findMeById(userId: UUID): Option<MeView> =
        UserTable.select { UserTable.id eq userId }
            .limit(1)
            .map {
                MeView(
                    it[UserTable.mailAddress],
                    searchRoleNames(userId),
                    searchPermissionNames(userId),
                    searchOperationNames(userId)
                )
            }
            .firstOrNone()

    private fun searchRoleNames(userId: UUID): Set<String> =
        (RoleTable innerJoin SubjectTable)
            .select { SubjectTable.user eq userId }
            .map { it[RoleTable.name] }
            .toSet()

    private fun searchPermissionNames(userId: UUID): Set<String> =
        (SubjectTable
            .join(
                RolePermissionTable,
                JoinType.INNER,
                onColumn = SubjectTable.role,
                otherColumn = RolePermissionTable.role
            )
                innerJoin PermissionTable)
            .select { SubjectTable.user eq userId }
            .map { it[PermissionTable.name] }
            .toSet()

    private fun searchOperationNames(userId: UUID): Set<String> =
        (SubjectTable
            .join(
                RolePermissionTable,
                JoinType.INNER,
                onColumn = SubjectTable.role,
                otherColumn = RolePermissionTable.role
            )
            .join(
                PermissionOperationTable,
                JoinType.INNER,
                onColumn = RolePermissionTable.permission,
                otherColumn = PermissionOperationTable.permission
            )
                innerJoin OperationTable)
            .select { SubjectTable.user eq userId }
            .map { it[OperationTable.name] }
            .toSet()
}