package com.codependent.weatherapp.controller

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertTrue

object WeatherControllerSpec : Spek({
    describe("WeatherController Suite") {
        var embeddedServer: EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
        var client: HttpClient = HttpClient.create(embeddedServer.url)

        it("test /weather") {
            val rsp = client.toBlocking().retrieve("/weather/sync?cityIds=3117735")
            assertTrue {
                rsp.contains("Madrid")
            }
        }

        afterGroup {
            client.close()
            embeddedServer.close()
        }
    }
})
