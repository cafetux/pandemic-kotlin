package org.pandemic.propagation

import org.pandemic.city.CityName
import org.pandemic.city.InfectionStatus

interface InfectionService {

    fun infect(city: CityName) : List<InfectionStatus>
}