package com.cps2.energy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cps2.energy.viewmodels.EditProfileViewModel

@Composable
fun EditProfileScreen(
        userId: Long,
        onBackClick: () -> Unit,
        viewModel: EditProfileViewModel = viewModel()
) {
    val user by viewModel.user.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()

    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(userId) { viewModel.loadUser(userId) }

    LaunchedEffect(user) {
        user?.let {
            fullName = it.fullName
            username = it.username
            email = it.email
        }
    }

    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            onBackClick()
        }
    }

    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 16.dp)
    ) {
        when {
            loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                ) {
                    Text(text = error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBackClick) { Text("Go Back") }
                }
            }
            user != null -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                            text = "Edit Profile",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            OutlinedTextField(
                                    value = fullName,
                                    onValueChange = { fullName = it },
                                    label = { Text("Full Name") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                    value = username,
                                    onValueChange = { username = it },
                                    label = { Text("Username") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                    text = "Role: ${user!!.role}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                            onClick = {
                                viewModel.updateUser(
                                        userId = userId,
                                        fullName = fullName,
                                        username = username,
                                        email = email
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled =
                                    fullName.isNotBlank() &&
                                            username.isNotBlank() &&
                                            email.isNotBlank()
                    ) { Text("Save") }
                }
            }
        }
    }
}
