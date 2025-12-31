package io.booookstore.command

import org.junit.jupiter.api.Test

class MatchMakeCommandTest {

    @Test
    fun execute() {
        val args = arrayOf(
            "--users-file-path", "TODO",
            "--combinations-history-file-path", "TODO"
        )

        MatchMakeCommand(args).execute()
    }

}