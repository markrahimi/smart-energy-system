package com.cps2.energy.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SensorData(
        val id: String,
        val deviceId: String,
        val temperature: Double?,
        val humidity: Double?,
        val luminosity: Double?,
        val powerConsumption: Double?,
        val voltage: Double?,
        val current: Double?,
        val timestamp: String
)
