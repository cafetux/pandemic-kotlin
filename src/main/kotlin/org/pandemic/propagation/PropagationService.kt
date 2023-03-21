package org.pandemic.propagation

import org.pandemic.city.Infected
import org.pandemic.city.Outbreak


class PropagationService(val infectionLevel: InfectionLevels, val infectionService: InfectionService, val outbreakCounter:OutbreakIndicator) {

    fun propagate(card: InfectionCard) {

        val toInfect = card.city
        val infectionStatus = infectionService.infect(toInfect)

        infectionStatus.forEach {
            when(it) {
                is Outbreak -> outbreakCounter.increase(1)
                is Infected -> infectionLevel.save(it.infectedCity)
            }
        }
    }
}