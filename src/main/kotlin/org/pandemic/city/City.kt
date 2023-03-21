package org.pandemic.city

data class City(val name: CityName, val infectionLevel: InfectionLevel) {

    fun infect(): InfectionStatus {
        return if (infectionLevel.isMaximum()) {
            Outbreak(name)
        } else {
            Infected(City(name, infectionLevel.increase()))
        }
    }

}
