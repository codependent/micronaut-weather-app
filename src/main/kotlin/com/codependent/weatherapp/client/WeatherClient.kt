package com.codependent.weatherapp.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Client("https://api.openweathermap.org/data/2.5")
interface WeatherClient {

    @Get("/weather?q={city}&appid={apiKey}&units=metric")
    fun getWeather(apiKey: String, city: String): Flux<Any>

    @Get("/weather?q={city},{country}&appid={apiKey}&units=metric")
    fun getWeather(apiKey: String, city: String, country: String): Mono<Any>

    @Get("/weather?id={cityId}&appid={apiKey}&units=metric")
    fun getWeatherByCityId(apiKey: String, cityId: String): Mono<Any>

    @Get("/group?id={cityIds}&appid={apiKey}&units=metric")
    fun getWeatherByCityIds(apiKey: String, cityIds: String): Flux<Any>
}