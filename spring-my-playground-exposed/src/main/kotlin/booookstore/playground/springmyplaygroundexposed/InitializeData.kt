package booookstore.playground.springmyplaygroundexposed

import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.OperationRepository
import booookstore.playground.springmyplaygroundexposed.adaptor.postgres.PermissionRepository
import booookstore.playground.springmyplaygroundexposed.domain.Permission
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InitializeData : ApplicationRunner {

    private val logger = LoggerFactory.getLogger(InitializeData::class.java)

    @Transactional
    override fun run(args: ApplicationArguments?) {
        OperationRepository().findByPermissionId("PE_001").let {
            logger.info("find permission {}", it)
        }
        OperationRepository().findByPermissionId("PE_002").let {
            logger.info("find permission {}", it)
        }

        Permission("PE_003", "VIEW_ALL_ORDER", setOf("OP_D001")).let {
            PermissionRepository().insert(it)
            logger.info("create permission {}", it)
        }
    }

}
