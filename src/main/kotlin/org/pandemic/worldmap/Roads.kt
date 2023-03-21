package org.pandemic.worldmap

import org.pandemic.city.CityName

class Roads(val links: List<Road>) {

    fun getNeighboursOf(city: CityName): List<CityName> {
        return links
            .filter { it.city1 == city || it.city2 == city}
            .map {
                return@map if (it.city1 == city) {
                    it.city2
                } else {
                    it.city1
                }
            }.toList()
    }
}
