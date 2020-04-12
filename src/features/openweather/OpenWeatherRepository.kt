package com.example.features.openweather

import com.example.features.cities.model.Location
import com.example.features.openweather.model.CityWeather
import com.example.features.openweather.model.GroupWeatherCities
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.Response
import okhttp3.OkHttpClient.Builder as Client
import okhttp3.Request.Builder as NewRequest

object OpenWeatherRepository {

    private const val API_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather"
    private const val API_WEATHER_GROUP_URL = "http://api.openweathermap.org/data/2.5/group"
    private const val API_WEATHER_KEY = "here_goes_your_api_key"

    private val mapper by lazy {
        jacksonObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
        )
    }

    //api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}

    fun getWeatherForHint(hint: String) =
        NewRequest()
            .url(
                "$API_WEATHER_URL?q=$hint&appid=$API_WEATHER_KEY"
            )
            .build()
            .let {
                Client()
                    .build()
                    .newCall(it)
                    .execute()
                    .takeIf(Response::isSuccessful)?.body?.string()
                    ?: throw RuntimeException("Error calling open weather")

            }
            .run { mapper.readValue<CityWeather>(this) }


    //api.openweathermap.org/data/2.5/weather?id={city id}&appid={your api key}

    fun getWeatherByCityId(id: String) = NewRequest()
        .url(
            "$API_WEATHER_URL?id=$id&appid=$API_WEATHER_KEY&lang=sp&units=metric"
        )
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
        .run { mapper.readValue<CityWeather>(this) }


    //api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={your api key}

    fun getWeatherForCities(ids: ArrayList<String>) =
        NewRequest().url("$API_WEATHER_GROUP_URL?id=${ids.joinToString(",")}&appid=$API_WEATHER_KEY").build()
            .also { println(it.url.toString()) }
            .let {
                Client()
                    .build()
                    .newCall(it)
                    .execute()
                    .takeIf(Response::isSuccessful)?.body?.string()
                    ?: throw RuntimeException("Error calling open weather")

            }
            .run { mapper.readValue<GroupWeatherCities>(this) }


    fun getWeatherByLatLon(location: Location) = NewRequest()
        .url(
            "$API_WEATHER_URL?lat=${location.lat}&lon=${location.lon}&appid=$API_WEATHER_KEY"
        )
        .build()
        .let {
            Client()
                .build()
                .newCall(it)
                .execute()
                .takeIf(Response::isSuccessful)?.body?.string()
                ?: throw RuntimeException("Error calling open weather")

        }
        .run { mapper.readValue<CityWeather>(this) }


}

