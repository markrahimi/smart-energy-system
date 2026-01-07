package com.cps2.energy.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Notification(
        val id: String,
        val userId: String,
        val title: String,
        val message: String,
        val type: String,
        val priority: String,
        val isRead: Boolean,
        val thresholdId: String?,
        val createdAt: String?
)
