package com.example.features.openweather.model

import com.example.features.cities.model.Location

data class CityWeather(
    val id: Int,
    val name: String,
    val coord: Location,
    val weather: List<Weather>,
    val base: String?,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val dt: Int,
    val clouds: Cloud
) {

    data class Weather(val id: Int, val main: String, val description: String, val icon: String)

    data class Main(
        val temp: Double,
        val pressure: Double,
        val humidity: Double,
        val tempMin: Double,
        val tempMax: Double

    )

    data class Wind(
        val speed: Double,
        val deg: Double
    )

    data class Cloud(
        val all: Double
    )
}