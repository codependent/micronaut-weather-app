package com.codependent.weatherapp.controller

import com.codependent.weatherapp.client.WeatherClient
import io.micronaut.context.annotation.Value
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import reactor.core.publisher.toMono
import java.util.*

@Controller("/weather")
class WeatherController(@Value("\${weather.api-key}") private val apiKey: String,
                        private val weatherClient: WeatherClient) {

    @Get(value = "/test1{?city,country,cityIds}", produces = [MediaType.APPLICATION_JSON])
    fun getWeather(city: Optional<String>, country: Optional<String>, cityIds: Optional<String>) = "The weather is...".toMono()

    @Get(value = "/test2", produces = [MediaType.APPLICATION_JSON])
    fun getWeather2(city: Optional<String>, country: Optional<String>, cityIds: Optional<String>) = "The weather is...".toMono()

    @Get(value = "/test3", produces = [MediaType.APPLICATION_JSON])
    fun getWeather3(city: String?, country: String?, cityIds: String?) = "The weather is...".toMono()
/*

    @Get(value = "/test1{?city,country,cityIds}", produces = [MediaType.APPLICATION_JSON])
    fun getWeather(city: Optional<String>, country: Optional<String>, cityIds: Optional<String>): Flux<Any> = weatherClient.getWeather(apiKey, city as String)
*/

    /*
        @Get(produces = [MediaType.APPLICATION_JSON])
        fun getWeather(city: String, country: String): Mono<Any> = weatherClient.getWeather(apiKey, city, country)

        @Get(produces = [MediaType.APPLICATION_JSON])
        fun getWeatherByCityIds(@Parameter("cityIds") cityIds: String) = weatherClient.getWeatherByCityIds(apiKey, cityIds)

    @Get("/sync", produces = [MediaType.APPLICATION_JSON])
    fun getWeatherByCityIdsCustomSync(@Parameter("cityIds") cityIds: String): Flux<Any> {
        val weatherByCities = mutableListOf<Any>()
        cityIds.split(",").forEach {
            val weatherByCityIdMono = weatherClient.getWeatherByCityId(apiKey, it)
            val result = weatherByCityIdMono.block()
            if (result != null) {
                weatherByCities.add(result)
            }
        }
        return weatherByCities.toFlux()
    }

    @Get("/async", produces = [MediaType.APPLICATION_JSON])
    fun getWeatherByCityIdsCustomAsync(@Parameter("cityIds") cityIds: String): Flux<Any> {
        val split = cityIds.split(",")
        return Flux.range(1, split.size)
                .flatMap {
                    weatherClient.getWeatherByCityId(apiKey, split[it - 1])
                }
    }
    */
}