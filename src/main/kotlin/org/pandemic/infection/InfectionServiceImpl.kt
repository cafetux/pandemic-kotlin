package org.pandemic.infection

import org.pandemic.city.*
import org.pandemic.worldmap.Roads

class InfectionServiceImpl(val worldMap : Roads) : InfectionService {


    override fun infect(city: CityName, cities: List<City>): List<InfectionStatus> {

        val toInfect = cities.first { it.name == city }

        return infect(toInfect, cities)
    }

    private fun infect(toInfect: City, cities : List<City>): List<InfectionStatus> {
        return infect(toInfect, cities, emptyList())
    }

    private fun infect(
        toInfect: City,
        cities: List<City>,
        alreadyOutbreaks : List<Outbreak>
    ): List<InfectionStatus> {
        return when (val infectionStatus = toInfect.infect()) {
            is Infected -> listOf(infectionStatus)
            is Outbreak -> listOf(infectionStatus) + neighbours(toInfect.name, cities)
                .filter { neighbour -> !alreadyOutbreak(alreadyOutbreaks, neighbour) }
                .flatMap { infect(it, cities, alreadyOutbreaks+infectionStatus) }
        }
    }

    private fun alreadyOutbreak(
        alreadyOutbreaks: List<Outbreak>,
        neighbour: City
    ) = alreadyOutbreaks.map { ao -> ao.outbreakCity }.contains(neighbour.name)

    private fun neighbours(city: CityName, cities : List<City>): List<City> {
        return worldMap.getNeighboursOf(city).map { find(cities,it) }
    }

    private fun find(cities: List<City>, city: CityName): City {
        return cities.first { it.name == city }
    }
}
