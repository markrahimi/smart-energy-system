package com.cps2.energy.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Device(
        val id: String,
        val name: String,
        val type: String,
        val status: String,
        val location: String?,
        val active: Boolean,
        val userId: String,
        val createdAt: String?
)
