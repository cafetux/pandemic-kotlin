package org.pandemic.infection

import org.assertj.core.api.Assertions
import org.junit.Ignore
import org.junit.jupiter.api.Test

import org.pandemic.city.*
import org.pandemic.worldmap.Road
import org.pandemic.worldmap.Roads
import org.pandemic.worldmap.road

internal class InfectionServiceImplTest {

    var cities = emptyList<City>()
    var roads = emptyList<Road>()

    @Test
    fun should_infect_city() {
        given_city("PARIS", 2)
        given_city("ESSEN", 0)
        given_city("ST-PETERSBOURG", 1)

        and_given_link_between("PARIS", "ESSEN")
        and_given_link_between("ESSEN", "ST-PETERSBOURG")

        val result = when_we_infect("PARIS")

        Assertions.assertThat(result).containsExactly(infected("PARIS", 3));

    }

    @Test
    fun should_outbreak_when_city_already_infected_3_times() {
        given_city("PARIS", 3)
        given_city("ESSEN", 1)
        given_city("MADRID", 0)
        given_city("ST-PETERSBOURG", 1)

        and_given_link_between("PARIS", "ESSEN")
        and_given_link_between("ESSEN", "ST-PETERSBOURG")
        and_given_link_between("MADRID", "PARIS")

        val result = when_we_infect("PARIS")

        Assertions.assertThat(result).containsExactlyInAnyOrder(
            infected("ESSEN", 2),
            infected("MADRID", 1),
            outbreak("PARIS")
        )

    }

    @Test
    fun should_chain_outbreaks() {
        given_city("PARIS", 3)
        given_city("ESSEN", 3)
        given_city("ST-PETERSBOURG", 0)
        given_city("MADRID", 0)

        and_given_link_between("PARIS", "ESSEN")
        and_given_link_between("ESSEN", "ST-PETERSBOURG")
        and_given_link_between("MADRID", "PARIS")
        and_given_link_between("MADRID", "ESSEN")

        val result = when_we_infect("PARIS")

        Assertions.assertThat(result).containsExactlyInAnyOrder(
            infected("MADRID", 2),
            infected("ST-PETERSBOURG", 1),
            outbreak("PARIS"),
            outbreak("ESSEN")
        )

    }

    private fun outbreak(cityName: String): InfectionStatus {
        return Outbreak(CityName(cityName))
    }

    private fun infected(cityName: String, expectedInfectionLevel: Int): InfectionStatus {
        return Infected(City(CityName(cityName), expectedInfectionLevel))
    }

    private fun when_we_infect(cityName: String): List<InfectionStatus> {
        return InfectionServiceImpl(Roads(roads)).infect(CityName(cityName), cities)
    }

    private fun and_given_link_between(city1: String, city2: String) {
        roads = roads + road(city1, city2)
    }

    private fun given_city(cityName: String, initialInfectionLevel: Int) {
        cities = cities + City(CityName(cityName), initialInfectionLevel)
    }

}