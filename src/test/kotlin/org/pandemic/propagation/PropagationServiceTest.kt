package org.pandemic.propagation

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.pandemic.city.*
import org.pandemic.infection.InfectionService

internal class PropagationServiceTest {


    private val infectionLevels: Cities = mockk<Cities>()
    private val infectionService: InfectionService = mockk<InfectionService>()
    private val outbreakIndicator: OutbreakIndicator = mockk<OutbreakIndicator>()

    private var expectedResult : List<InfectionStatus> = emptyList()

    val service = PropagationService(infectionLevels, infectionService, outbreakIndicator)

    @Test
    fun should_save_infection_level_when_city_is_infected() {

        given_a_city_without_infection("PARIS")

        when_we_propagate_infection_to("PARIS")

        then_we_have_infected("PARIS", 1)
    }

    @Test
    fun should_increase_outbreak_counter_when_city_outbreak_and_infect_neighbours() {

        given_a_city_with_maximum_infection_level("PARIS")
        and_neigbour("MADRID")
        and_neigbour("LONDRES")

        when_we_propagate_infection_to("PARIS")

        then_we_increase_outbreak_counter()
        then_we_have_infected("MADRID", 1)
        then_we_have_infected("LONDRES", 1)
    }

    private fun and_neigbour(cityName: String) {
        expectedResult = expectedResult + Infected(City(CityName(cityName), 1))
    }

    private fun then_we_increase_outbreak_counter() {
        verify { outbreakIndicator.increase(1) }
    }

    private fun given_a_city_with_maximum_infection_level(cityName: String) {
        justRun { outbreakIndicator.increase(any()) }
        expectedResult = expectedResult + Outbreak(CityName(cityName))
    }


    private fun then_we_have_infected(cityName: String, expectedInfectionLevel: Int) {
        verify { infectionLevels.save(City(CityName(cityName), expectedInfectionLevel)) }
    }

    private fun when_we_propagate_infection_to(cityName: String) {
        justRun { infectionLevels.save(any()) }
        every { infectionLevels.all() } returns emptyList()
        every { infectionService.infect(CityName(cityName), any()) } returns expectedResult
        service.propagate(InfectionCard(CityName(cityName)))
    }

    private fun given_a_city_without_infection(cityName: String) {
        expectedResult = expectedResult + Infected(City(CityName(cityName), 1))
    }
}