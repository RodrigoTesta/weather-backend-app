package com.example.features.openweather.model

data class OpenWeatherAllData(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val current: CurrentWeather,
    val hourly: List<CurrentWeather>,
    val daily: List<TemporalWeather>
) {

    data class CurrentWeather(
        val dt: Int,
        val sunrise: Int,
        val sunset: Int,
        val temp: Double,
        val pressure: Int,
        val humidity: Int,
        val uvi: Double,
        val clouds: Int,
        val visibility: Int,
        val wind_speed: Double,
        val wind_deg: Int,
        val weather: List<Weather>,
        val rain: Map<String, Double>?,
        val dew_point: Double
    )

    data class TemporalWeather(
        val dt: Int,
        val sunrise: Int,
        val sunset: Int,
        val temp: Temperature,
        val pressure: Int,
        val humidity: Int,
        val uvi: Double,
        val clouds: Int,
        val visibility: Int,
        val wind_speed: Double,
        val wind_deg: Int,
        val weather: List<Weather>,
        val rain: Double?,
        val dew_point: Double
    )

    data class Temperature(val day: Double, val night: Double, val eve: Double, val morn: Double, val max: Double?, val min: Double?)

}
