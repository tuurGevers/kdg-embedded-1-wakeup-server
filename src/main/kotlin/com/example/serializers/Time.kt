package com.example.serializers

import kotlinx.serialization.Serializable
import java.time.Instant
import com.example.serializers.InstantSerializer.InstantSerializer

@Serializable
data class Time( @Serializable(with = InstantSerializer::class) val time: Instant)
