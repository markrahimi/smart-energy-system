package com.cps2.energy.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
        val id: String,
        val username: String,
        val email: String,
        val fullName: String,
        val role: String,
        val createdAt: String?
)
