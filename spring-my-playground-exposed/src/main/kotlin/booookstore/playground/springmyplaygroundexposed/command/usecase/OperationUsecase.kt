package booookstore.playground.springmyplaygroundexposed.command.usecase

import booookstore.playground.springmyplaygroundexposed.command.domain.user.MailAddress
import booookstore.playground.springmyplaygroundexposed.command.domain.user.Operation
import booookstore.playground.springmyplaygroundexposed.command.domain.user.OperationRepository
import booookstore.playground.springmyplaygroundexposed.command.domain.user.RoleId
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