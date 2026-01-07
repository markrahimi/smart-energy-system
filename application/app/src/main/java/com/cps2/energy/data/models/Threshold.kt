package com.cps2.energy.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Threshold(
        val id: String,
        val deviceId: String,
        val userId: String,
        val minTemperature: Double?,
        val maxTemperature: Double?,
        val minHumidity: Double?,
        val maxHumidity: Double?,
        val minLuminosity: Double?,
        val maxLuminosity: Double?,
        val minPower: Double?,
        val maxPower: Double?,
        val minVoltage: Double?,
        val maxVoltage: Double?,
        val minCurrent: Double?,
        val maxCurrent: Double?,
        val isActive: Boolean,
        val createdAt: String?
)
