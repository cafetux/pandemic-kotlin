package org.pandemic.worldmap

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.pandemic.city.CityName

internal class RoadsTest {

    @Test
    fun should_retrieve_only_direct_neighbours() {
        val worldMap = Roads(
            listOf(
                road("PARIS", "LONDRES"),
                road("PARIS", "MILAN"),
                road("PARIS", "MADRID"),
                road("PARIS", "ALGERS"),
                road("ESSEN", "PARIS"),
                road("ESSEN", "ST-PETERSBOURG"),
                road("NEW-YORK", "LONDRES")
            ))

        assertThat(worldMap.getNeighboursOf(CityName("PARIS")))
            .containsExactlyInAnyOrder(
                CityName("LONDRES"),
                CityName("ALGERS"),
                CityName("ESSEN"),
                CityName("MILAN"),
                CityName("MADRID")
            )
    }

}

fun road(city1: String, city2: String) = Road(CityName(city1), CityName(city2))
