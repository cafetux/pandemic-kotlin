package org.pandemic.city

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CityTest {

    @Test
    fun should_infect_a_city() {
        val city = city("PARIS", 0)

        val result = city.infect()

        assertThat(result is Infected).isTrue
        assertThat((result as Infected).infectedCity.infectionLevel.value).isEqualTo(1)
    }


    @Test
    fun should_infect_a_city_twice() {
        val city = city("PARIS", 1)

        val result = city.infect()

        assertThat(result is Infected).isTrue
        assertThat((result as Infected).infectedCity.infectionLevel.value).isEqualTo(2)
    }

    @Test
    fun should_infect_a_city_thrice() {
        val city = city("PARIS", 2)

        val result = city.infect()

        assertThat(result is Infected).isTrue
        assertThat((result as Infected).infectedCity.infectionLevel.value).isEqualTo(3)
    }

    @Test
    fun should_outbreak_instead_of_fourth_infection() {
        val city = city("PARIS", 3)

        val result = city.infect()

        assertThat(result is Outbreak).isTrue
        assertThat((result as Outbreak).outbreakCity.name).isEqualTo("PARIS")
    }


}

fun city(name: String, infectionLevel: Int) = City(CityName(name), InfectionLevel(infectionLevel))

