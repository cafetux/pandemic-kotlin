package org.pandemic.propagation

import org.pandemic.city.City

interface Cities {

    fun save(city : City)

    fun all() : List<City>

}