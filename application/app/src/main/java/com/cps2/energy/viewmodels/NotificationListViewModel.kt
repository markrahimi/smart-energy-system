package com.cps2.energy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cps2.energy.data.api.RetrofitClient
import com.cps2.energy.data.models.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationListViewModel : ViewModel() {
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadNotifications(userId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _notifications.value = RetrofitClient.apiService.getNotifications(userId = userId)
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Failed to load notifications: ${e.localizedMessage ?: e.message}"
                _loading.value = false
            }
        }
    }
}
