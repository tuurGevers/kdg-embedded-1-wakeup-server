package com.example.serializers

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Data(@Serializable(with = InstantSerializer.InstantSerializer::class) var wakeup: Instant,
                val rates : MutableList<Double>,
                var wake:Boolean,)
