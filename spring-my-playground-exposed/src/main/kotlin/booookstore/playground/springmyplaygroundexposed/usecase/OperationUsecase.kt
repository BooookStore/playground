package booookstore.playground.springmyplaygroundexposed.usecase

import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.OperationRepository
import booookstore.playground.springmyplaygroundexposed.domain.MailAddress
import booookstore.playground.springmyplaygroundexposed.domain.Operation
import booookstore.playground.springmyplaygroundexposed.domain.RoleId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OperationUsecase(private val operationRepository: OperationRepository) {

    private val logger = LoggerFactory.getLogger(OperationUsecase::class.java)

    @Transactional(readOnly = true)
    fun findOperationByRoleId(roleId: RoleId): List<Operation> {
        return operationRepository.findByRoleId(roleId)
    }

    @Transactional(readOnly = true)
    fun findOperationByUserMailAddress(mailAddress: MailAddress): List<Operation> {
        return operationRepository.findByUserMailAddress(mailAddress)
    }

    @Transactional
    fun createOperation(operation: Operation) {
        operationRepository.saveAsNew(operation)
        logger.info("saved as new operation {}", operation)
    }

}