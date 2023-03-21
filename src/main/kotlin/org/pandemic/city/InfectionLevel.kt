package org.pandemic.city

data class InfectionLevel(val value: Int) {

    companion object {
        val MAX_INFECTION_LEVEL_VALUE = 3
    }
    init {
        require(value >= 0)
        require(value <= MAX_INFECTION_LEVEL_VALUE)
    }

    fun increase(): InfectionLevel {
        return InfectionLevel(value + 1)
    }

    fun isMaximum(): Boolean {
        return value == MAX_INFECTION_LEVEL_VALUE
    }


}