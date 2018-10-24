package com.codependent.weatherapp.controller

import com.codependent.weatherapp.client.WeatherClient
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Value
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import javax.annotation.Nullable

@Controller("/weather")
class WeatherController(@Value("\${weather.api-key}") private val apiKey: String,
                        private val weatherClient: WeatherClient) {

    @Get(value = "{?city,country,cityIds}", produces = [MediaType.APPLICATION_JSON])
    fun getWeather(@Nullable city: String?, @Nullable country: String?, @Nullable cityIds: String?): Flux<Any> {
        return if (city != null && country != null && cityIds == null) {
            weatherClient.getWeather(apiKey, city, country).toFlux()
        } else if (city != null && country == null && cityIds == null) {
            weatherClient.getWeather(apiKey, city)
        } else if (city == null && country == null && cityIds != null) {
            weatherClient.getWeatherByCityIds(apiKey, cityIds)
        } else {
            Flux.empty<Any>()
        }
    }

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

    /*
    @Get(value = "/test1{?city,country,cityIds}", produces = [MediaType.APPLICATION_JSON])
    fun getWeather(city: Optional<String>, country: Optional<String>, cityIds: Optional<String>) = "The weather is...".toMono()

    @Get(value = "/test2", produces = [MediaType.APPLICATION_JSON])
    fun getWeather2(city: Optional<String>, country: Optional<String>, cityIds: Optional<String>) = "The weather is...".toMono()
*/

}