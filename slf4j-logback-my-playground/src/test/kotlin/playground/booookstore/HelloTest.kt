package playground.booookstore

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.core.util.StatusPrinter
import org.junit.Test
import org.slf4j.LoggerFactory

class Playground {

    @Test
    fun basicTemplateForLogging() {
        val logger = LoggerFactory.getLogger(Playground::class.java)
        logger.debug("Hello World.")
        // -> 16:35:31.868 [main] DEBUG playground.booookstore.Playground -- Hello World.

        // print internal state
        val lc = LoggerFactory.getILoggerFactory() as LoggerContext
        StatusPrinter.print(lc)
    }

}
