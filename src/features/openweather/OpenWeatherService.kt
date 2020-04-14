package com.example.features.openweather

import com.example.features.openweather.model.CityForecast
import kotlin.math.pow
import kotlin.math.roundToInt

object OpenWeatherService {
    private const val API_WEATHER_ICON = "https://openweathermap.org/img/wn/{icon}@2x.png"

    fun getWeatherByCityId(id: String, language: String, unit: String) =
        OpenWeatherRepository.getWeatherByCityId(id, language, unit).let(::enhanceWeatherCity)


    fun getWeatherForTopFiveCities(language: String, unit: String) =
        OpenWeatherRepository.getWeatherForCities(
            arrayListOf(
                "5128581",
                "3433955",
                "3432039",
                "3433922"
            ), language, unit
        ).list.map(::enhanceWeatherCity)

    fun getOneCallWeather(latitude: Double, longitude: Double, language: String, unit: String) =
        OpenWeatherRepository.getWeatherOneCall(latitude, longitude, language, unit)

    private fun enhanceWeatherCity(cityForecast: CityForecast) =
        cityForecast.copy(
            visibility = cityForecast.visibility / 1000,
            main = cityForecast.main.copy(temp = cityForecast.main.temp.round(1))
        )

    fun Double.round(decimals: Int = 2): Double =
        10.toDouble().pow(decimals.toDouble()).let { (this * it).roundToInt() / it }
}