package io.booookstore.usecase

import io.booookstore.domain.PotentialCombinations
import io.booookstore.port.UsersPort

class MatchMakeUsecase(val usersPort: UsersPort) {

    fun execute(): PotentialCombinations {
        val users = usersPort.findUsers()
        TODO()
    }

}
