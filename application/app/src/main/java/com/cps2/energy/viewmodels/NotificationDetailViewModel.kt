package com.cps2.energy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cps2.energy.data.api.RetrofitClient
import com.cps2.energy.data.models.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationDetailViewModel : ViewModel() {
    private val _notification = MutableStateFlow<Notification?>(null)
    val notification: StateFlow<Notification?> = _notification.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadNotification(notificationId: Long) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _notification.value = RetrofitClient.apiService.getNotificationById(notificationId)
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "failed load notification:${e.localizedMessage ?: e.message}"
                _loading.value = false
            }
        }
    }
}
