package com.cps2.energy.data.models


data class Device(
    val id: Long,
    val name: String,
    val type: String,
    val status: String,
    val location: String?,
    val active: Boolean,
    val createdAt: String?
)
