package com.cps2.energy.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SensorData(
    val id: Long,
    val device: Device,
    val temperature: Double?,
    val humidity: Double?,
    val luminosity: Double?,
    val distance: Double?,
    val timestamp: String
)
