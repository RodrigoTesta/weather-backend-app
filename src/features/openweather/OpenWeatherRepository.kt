package com.example.features.openweather

import com.example.features.cities.model.Location
import com.example.features.openweather.model.CityForecast
import com.example.features.openweather.model.ExtendedForecast
import com.example.features.openweather.model.ForecastData
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.Response
import okhttp3.OkHttpClient.Builder as Client
import okhttp3.Request.Builder as NewRequest

object OpenWeatherRepository {

    private const val API_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather"
    private const val API_WEATHER_GROUP_URL = "https://api.openweathermap.org/data/2.5/group"
    private const val API_WEATHER_ONE_CALL = "https://api.openweathermap.org/data/2.5/onecall"
    private const val API_WEATHER_KEY = "open_weather_api_key"

    private val mapper by lazy {
        jacksonObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
        ).registerModule(KotlinModule())
    }

    //api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}

    fun getWeatherForHint(hint: String) =
        NewRequest()
            .url("$API_WEATHER_URL?q=$hint&appid=$API_WEATHER_KEY")
            .build()
            .let {
                Client()
                    .build()
                    .newCall(it)
                    .execute()
                    .takeIf(Response::isSuccessful)?.body?.string()
                    ?: throw RuntimeException("Error calling open weather")

            }
            .run { mapper.readValue<CityForecast>(this) }


    //api.openweathermap.org/data/2.5/weather?id={city id}&appid={your api key}

    fun getWeatherByCityId(id: String, language: String, unit: String) =
        NewRequest()
            .url("$API_WEATHER_URL?id=$id&appid=$API_WEATHER_KEY&lang=$language&units=$unit")
            .build()
            .also { println(it.url.toString()) }
            .let {
                Client()
                    .build()
                    .newCall(it)
                    .execute()
                    .takeIf(Response::isSuccessful)?.body?.string()
                    ?: throw RuntimeException("Error calling open weather")

            }
            .run { mapper.readValue<CityForecast>(this) }


    //api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={your api key}

    fun getWeatherForCities(ids: ArrayList<String>, language: String, unit: String) =
        NewRequest()
            .url("$API_WEATHER_GROUP_URL?id=${ids.joinToString(",")}&lang=$language&units=$unit&appid=$API_WEATHER_KEY")
            .build()
            .also { println(it.url.toString()) }
            .let {
                Client()
                    .build()
                    .newCall(it)
                    .execute()
                    .takeIf(Response::isSuccessful)?.body?.string()
                    ?: throw RuntimeException("Error calling open weather")

            }
            .run { mapper.readValue<ForecastData>(this) }


    fun getWeatherByLatLon(location: Location, language: String, unit: String) =
        NewRequest()
            .url("$API_WEATHER_URL?lat=${location.lat}&lon=${location.lon}&lang=$language&units=$unit&appid=$API_WEATHER_KEY)")
            .build()
            .let {
                Client()
                    .build()
                    .newCall(it)
                    .execute()
                    .takeIf(Response::isSuccessful)?.body?.string()
                    ?: throw RuntimeException("Error calling open weather")

            }
            .run { mapper.readValue<CityForecast>(this) }

    //https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&appid={YOUR API KEY}

    fun getWeatherOneCall(latitude: Double, longitude: Double, language: String, unit: String) =
        NewRequest()
            .url("$API_WEATHER_ONE_CALL?lat=${latitude}&lon=${longitude}&lang=$language&units=$unit&appid=$API_WEATHER_KEY")
            .build()
            .let {
                Client()
                    .build()
                    .newCall(it)
                    .execute()
                    .takeIf(Response::isSuccessful)?.body?.string()
                    ?: throw RuntimeException("Error calling open weather")

            }
            .run { mapper.readValue<ExtendedForecast>(this) }
}

