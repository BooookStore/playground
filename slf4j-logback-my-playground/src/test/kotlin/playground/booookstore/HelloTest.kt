package playground.booookstore

import ch.qos.logback.classic.Level.INFO
import ch.qos.logback.classic.Level.TRACE
import ch.qos.logback.classic.Logger
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

    /**
     * ログレベルは明示的に指定されない限り、親のロガーから継承される。
     * - com.foo          INFO
     * - com.foo.Bar      INFO (silently)
     * - com.foo.Bar.Foo  TRACE
     *
     * 参考
     * - https://logback.qos.ch/manual/architecture.html#effectiveLevel
     * - https://logback.qos.ch/manual/architecture.html#basic_selection
     */
    @Test
    fun loggerLevel() {
        val logger = LoggerFactory.getLogger("com.foo") as Logger
        logger.level = INFO

        val barlogger = LoggerFactory.getLogger("com.foo.Bar")
        val foologger = LoggerFactory.getLogger("com.foo.Bar.Foo") as Logger
        foologger.level = TRACE

        logger.warn("Low fuel level.")
        // -> 20:28:02.203 [main] WARN com.foo -- Low fuel level.
        logger.debug("Starting search for nearest gas station.")
        // no output
        barlogger.info("Located nearest gas station.")
        // -> 20:28:02.205 [main] INFO com.foo.Bar -- Located nearest gas station.
        foologger.trace("Display street address.")
        // -> 16:19:08.997 [main] TRACE com.foo.Bar.Foo -- Display street address.
        barlogger.debug("Exiting gas station search.")
        // no output
    }

}
