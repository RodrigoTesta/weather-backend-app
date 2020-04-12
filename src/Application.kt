package com.example

import com.example.features.cities.CitiesRepository
import com.example.features.openweather.OpenWeatherService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val client = HttpClient(OkHttp) {
    }

    install(ContentNegotiation) {
        jackson {}
    }

    /*
        This is just for test purposes, not valid for production.
     */
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }

    install(StatusPages) {
        exception<java.lang.IllegalArgumentException> { cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf(
                    "status" to HttpStatusCode.BadRequest.value,
                    "message" to "Bad Request",
                    "cause" to cause.message
                )
            )
        }
        exception<java.lang.NullPointerException> { cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf(
                    "status" to HttpStatusCode.InternalServerError.value,
                    "message" to HttpStatusCode.InternalServerError.description,
                    "cause" to cause.message
                )
            )
        }
    }

    routing {
        get("/suggestions/cities/{hint}") {
            call.parameters["hint"]?.let { hint ->
                call.respond(CitiesRepository.getSuggestions(hint))
            } ?: run {
                throw IllegalArgumentException("Hint must be provided")
            }
        }

        get("/weather/cities") {
            call.respond(
                OpenWeatherService.getWeatherForTopFiveCities()
            )
        }

        get("/weather/cities/{city}") {
            call.parameters["city"]?.let { city ->
                call.respond(OpenWeatherService.getWeatherByCityId(city))
            } ?: run {
                throw IllegalArgumentException("City must be provided")
            }

        }

    }
}
