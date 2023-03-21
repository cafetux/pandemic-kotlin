package org.pandemic.city

data class City(val name: CityName, val infectionLevel: Int) {
    init {
        require(infectionLevel >= 0)
        require(infectionLevel <= 3)
    }

    fun infect(): InfectionStatus {
        return if(infectionLevel < 3) {
            Infected(City(name, infectionLevel + 1))
        } else {
            Outbreak(name)
        }
    }

}
