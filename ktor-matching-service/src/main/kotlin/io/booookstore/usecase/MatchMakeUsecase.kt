package io.booookstore.usecase

import io.booookstore.domain.BeforeCombination
import io.booookstore.domain.PotentialCombinations
import io.booookstore.domain.Self
import io.booookstore.port.CombinationsHistoryPort
import io.booookstore.port.UsersPort

class MatchMakeUsecase(val usersPort: UsersPort, val combinationsHistoryPort: CombinationsHistoryPort) {

    fun execute(): PotentialCombinations {
        val users = usersPort.findUsers()

        val potentialCombinations = PotentialCombinations
            .matchMakeEvery(users)
            .excludeSpeakers(Self)
            .excludeSpeakers(BeforeCombination(combinationsHistoryPort.find()))

        return potentialCombinations
    }

}
