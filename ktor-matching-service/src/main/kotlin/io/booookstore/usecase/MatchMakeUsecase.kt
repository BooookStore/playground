package io.booookstore.usecase

import io.booookstore.domain.ExcludeBeforeCombination
import io.booookstore.domain.PotentialCombinations
import io.booookstore.domain.ExcludeSelf
import io.booookstore.port.CombinationsHistoryPort
import io.booookstore.port.UsersPort

class MatchMakeUsecase(val usersPort: UsersPort, val combinationsHistoryPort: CombinationsHistoryPort) {

    fun execute(): PotentialCombinations {
        val users = usersPort.findUsers()

        val potentialCombinations = PotentialCombinations
            .matchMakeEvery(users)
            .narrowDown(ExcludeSelf)
            .narrowDown(ExcludeBeforeCombination(combinationsHistoryPort.find()))

        return potentialCombinations
    }

}
