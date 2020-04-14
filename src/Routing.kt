package com.example

import com.example.features.cities.CitiesRepository
import com.example.features.openweather.OpenWeatherService
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get


fun Route.suggestions() {
    get("/suggestions/cities/{hint}") {
        call.parameters["hint"]?.let { hint ->
            call.respond(CitiesRepository.getSuggestions(hint))
        } ?: run {
            throw IllegalArgumentException("Hint must be provided")
        }
    }
}

fun Route.weather() {
    get("/weather/cities") {
        val lang = call.parameters["language"] ?: "sp"
        val unit = call.parameters["unit"] ?: "metric"
        call.respond(
            OpenWeatherService.getWeatherForTopFiveCities(lang, unit)
        )
    }

    get("/weather/cities/{city}") {
        val lang = call.parameters["language"] ?: "sp"
        val unit = call.parameters["unit"] ?: "metric"
        call.parameters["city"]?.let { city ->
            call.respond(OpenWeatherService.getWeatherByCityId(city, lang, unit))
        } ?: run {
            throw IllegalArgumentException("City must be provided")
        }
    }

    get("/weather/all") {

        val lat = call.parameters["latitude"]?.toDouble()
            ?: throw IllegalArgumentException("Latitude is a mandatory parameter")
        val lon = call.parameters["longitude"]?.toDouble()
            ?: throw IllegalArgumentException("Longitude is a mandatory parameter")
        val lang = call.parameters["language"] ?: "sp"
        val unit = call.parameters["unit"] ?: "metric"

        call.respond(OpenWeatherService.getOneCallWeather(lat, lon, lang, unit))

    }
}