package com.codependent.weatherapp.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Client("https://api.openweathermap.org/data/2.5")
interface WeatherClient {

    @Get("/weather?q={city}&appid={apiKey}")
    fun getWeather(apiKey: String, city: String): Flux<Any>

    @Get("/weather?q={city},{country}&appid={apiKey}")
    fun getWeather(apiKey: String, city: String, country: String): Mono<Any>

    @Get("/weather?id={cityId}&appid={apiKey}")
    fun getWeatherByCityId(apiKey: String, cityId: String): Mono<Any>

    @Get("/weather?id={cityIds}&appid={apiKey}")
    fun getWeatherByCityIds(apiKey: String, cityIds: String): Flux<Any>
}