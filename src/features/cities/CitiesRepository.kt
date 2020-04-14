package com.example.features.cities

import com.example.features.cities.model.Suggestion
import com.example.features.cities.model.City
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES as UnknownProps


object CitiesRepository {

    private val mapper by lazy { jacksonObjectMapper().configure(UnknownProps, false) }
    private val cities by lazy {
        mapper.readValue<List<City>>(File("path_to_cities.json"))
    }

    fun getSuggestions(hint: String) =
        cities
            .filter { it.name.contains(hint, true) }
            .map {
                Suggestion(
                    it.name,
                    it.name.replace(Regex("(?i)$hint"), "<em>$0</em>"),
                    it.id,
                    it.coord
                )
            }
}