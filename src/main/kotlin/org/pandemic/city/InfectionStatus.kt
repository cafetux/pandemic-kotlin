package org.pandemic.city

sealed class InfectionStatus
data class Infected(val infectedCity: City) : InfectionStatus()
data class Outbreak(val outbreakCity: CityName) : InfectionStatus()