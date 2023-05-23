package com.example.routes

import com.example.Data
import com.example.serializers.Time
import com.fazecast.jSerialComm.SerialPort
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalTime
import java.util.*

fun Routing.homeRouting() {
    var count = 0;
    route("/home"){
        post("/"){
            val params = runCatching { call.receive<String>().toDouble() }.getOrNull()?:0.0
            Data.rates.add(params)
        }
        get("/"){
            call.respond(com.example.serializers.Data(Data.wakeup,Data.rates,Data.wake))
        }
        get("/time"){

            val targetTime = Data.wakeup

            val currentTime = Instant.now()
            val comparisonResult = currentTime.compareTo(targetTime)

            val response = when {
                comparisonResult < 0 -> 0
                comparisonResult > 0 -> 1
                else -> 1
            }

            call.respond(HttpStatusCode.OK, response)
        }

        post("set-time"){
            val time = call.runCatching { call.receive<Time>() }.getOrNull()?:return@post call.respondText("eror parsing time", status= HttpStatusCode.BadRequest)
            Data.wakeup = time.time
            call.respondText("time set correctly", status = HttpStatusCode.OK)
        }
        post("wake"){
            Data.wake = true
            call.respond("wake")
        }

    }

}

