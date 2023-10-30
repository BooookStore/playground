package booookstore.playground.springmyplaygroundexposed.usecase

import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.OperationRepository
import booookstore.playground.springmyplaygroundexposed.domain.Operation
import booookstore.playground.springmyplaygroundexposed.domain.RoleId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OperationUsecase(private val operationRepository: OperationRepository) {

    @Transactional(readOnly = true)
    fun findOperationByRoleId(roleId: RoleId): List<Operation> {
        return operationRepository.findByRoleId(roleId)
    }

}