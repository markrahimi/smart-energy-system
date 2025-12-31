package com.cps2.energy.data.models

data class Notification(
    val id: Long,
    val title: String,
    val message: String,
    val type: String,
    val priority: String,
    val isRead: Boolean,
    val createdAt: String?
)
