package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import java.time.Instant

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
        allowHost("127.0.0.1:5173")
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowCredentials = true // set this to true if you're using cookies for authentication

    }
    configureSerialization()
    configureRouting()
}

object Data{
    var wakeup:Instant = Instant.now();
    val rates : MutableList<Double> = mutableListOf();
    var wake = false;
}