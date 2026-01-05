package com.cps2.energy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cps2.energy.data.api.RetrofitClient
import com.cps2.energy.data.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    fun loadUser(userId: Long) {

        viewModelScope.launch {
            try {
                _loading.value = true
                _user.value = RetrofitClient.apiService.getUserById(userId)
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Failed to load user: ${e.localizedMessage ?: e.message}"
                _loading.value = false
            }
        }
    }

    fun updateUser(userId: Long, fullName: String, username: String, email: String) {

        viewModelScope.launch {
            try {
                _loading.value = true
                val currentUser = _user.value
                if (currentUser != null) {
                    val updatedUser =
                            currentUser.copy(
                                    fullName = fullName,
                                    username = username,
                                    email = email
                            )
                    RetrofitClient.apiService.updateUser(userId, updatedUser)
                    _saveSuccess.value = true
                }
                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Failed to update user: ${e.localizedMessage ?: e.message}"
                _loading.value = false
            }
        }
    }
}
