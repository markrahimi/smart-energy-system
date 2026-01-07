package com.cps2.energy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cps2.energy.data.api.RetrofitClient
import com.cps2.energy.data.models.Device
import com.cps2.energy.data.models.SensorData
import com.cps2.energy.data.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices.asStateFlow()

    private val _sensorDataMap = MutableStateFlow<Map<String, SensorData>>(emptyMap())
    val sensorDataMap: StateFlow<Map<String, SensorData>> = _sensorDataMap.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadUser(userId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _user.value = RetrofitClient.apiService.getUserById(userId)

                // Fetch devices for this user
                _devices.value = RetrofitClient.apiService.getDevices(userId = userId)

                // Fetch sensor data for each device
                val sensorMap = mutableMapOf<String, SensorData>()
                _devices.value.forEach { device ->
                    try {
                        val sensorData = RetrofitClient.apiService.getLatestSensorData(device.id)
                        sensorMap[device.id] = sensorData
                    } catch (e: Exception) {
                        // Ignore if no sensor data available
                    }
                }
                _sensorDataMap.value = sensorMap
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Failed to load user: ${e.localizedMessage ?: e.message}"
                _loading.value = false
            }
        }
    }
}
