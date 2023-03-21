package org.pandemic.infection

import org.pandemic.city.City
import org.pandemic.city.CityName
import org.pandemic.city.InfectionStatus

interface InfectionService {

    fun infect(city: CityName, cities : List<City>) : List<InfectionStatus>
}