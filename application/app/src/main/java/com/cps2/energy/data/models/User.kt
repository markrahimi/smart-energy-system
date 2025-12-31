package com.cps2.energy.data.models

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val fullName: String,
    val role: String,
    val createdAt: String?,
    val devices: List<Device>? = null,
    val notifications: List<Notification>? = null

)
