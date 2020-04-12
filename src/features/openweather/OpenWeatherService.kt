package com.example.features.openweather

import com.example.features.openweather.model.CityWeather

object OpenWeatherService {
    private const val API_WEATHER_ICON = "https://openweathermap.org/img/wn/{icon}@2x.png"

    fun getWeatherByCityId(id: String) =
        OpenWeatherRepository.getWeatherByCityId(id).let(::enhanceWeatherCity)


    fun getWeatherForTopFiveCities() =
        OpenWeatherRepository.getWeatherForCities(
            arrayListOf(
                "5128581",
                "3433955",
                "3432039",
                "3433922"
            )
        ).list.map(::enhanceWeatherCity)


    private fun enhanceWeatherCity(cityWeather: CityWeather) =
        cityWeather.copy(
            weather = cityWeather.weather.map { weather ->
                weather.copy(icon = API_WEATHER_ICON.replace("{icon}", weather.icon))
            },
            visibility = cityWeather.visibility / 1000,
            main = cityWeather.main.copy(temp = cityWeather.main.temp.round(1))
        )

    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
}