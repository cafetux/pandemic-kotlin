package org.pandemic.propagation

import org.pandemic.city.Infected
import org.pandemic.city.Outbreak
import org.pandemic.infection.InfectionService


class PropagationService(val cities: Cities, val infectionService: InfectionService, val outbreakCounter:OutbreakIndicator) {

    fun propagate(card: InfectionCard) {

        val toInfect = card.city
        val infectionStatus = infectionService.infect(toInfect, cities.all())

        infectionStatus.forEach {
            when(it) {
                is Outbreak -> outbreakCounter.increase(1)
                is Infected -> cities.save(it.infectedCity)
            }
        }
    }
}