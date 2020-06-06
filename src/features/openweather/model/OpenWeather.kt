package com.example.features.openweather.model

private const val API_WEATHER_ICON = "https://openweathermap.org/img/wn/{icon}@2x.png"

data class Weather(val id: Int, val main: String, val description: String, val icon: String) {
    val iconUrl: String = API_WEATHER_ICON.replace("{icon}", icon)
}