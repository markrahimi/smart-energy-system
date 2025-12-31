package com.cps2.energy.data.models


data class SensorData(
    val id: Long,
    val device: Device,
    val temperature: Double?,
    val humidity: Double?,
    val luminosity: Double?,
    val distance: Double?,
    val timestamp: String
)
