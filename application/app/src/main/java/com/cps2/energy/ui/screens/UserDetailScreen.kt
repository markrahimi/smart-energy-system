package com.cps2.energy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cps2.energy.viewmodels.UserDetailViewModel

@Composable
fun UserDetailScreen(
        userId: Long,
        onBackClick: () -> Unit,
        onNotificationClick: (Long) -> Unit,
        onEditProfileClick: (Long) -> Unit,
        viewModel: UserDetailViewModel = viewModel()
) {
    val user by viewModel.user.collectAsState()
    val sensorDataMap by viewModel.sensorDataMap.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(userId) { viewModel.loadUser(userId) }

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
                LazyColumn {
                    item {
                        Text(
                                text = "User Details",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    item {
                        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                DetailRow("ID", user!!.id.toString())
                                DetailRow("Full Name", user!!.fullName)
                                DetailRow("Username", user!!.username)
                                DetailRow("Email", user!!.email)
                                DetailRow("Role", user!!.role)
                            }
                        }
                    }

                    item {
                        Button(
                                onClick = { onEditProfileClick(userId) },
                                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        ) { Text("Edit Profile") }
                    }

                    item {
                        Text(
                                text = "Devices (${user!!.devices?.size ?: 0})",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    if (user!!.devices.isNullOrEmpty()) {
                        item {
                            Text(
                                    text = "No devices found",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(8.dp)
                            )
                        }
                    } else {
                        items(user!!.devices!!) { device ->
                            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                            text = device.name,
                                            style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                            text = "Type: ${device.type}",
                                            style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                            text = "Status: ${device.status}",
                                            style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Text(
                                text = "Notifications (${user!!.notifications?.size ?: 0})",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                    }

                    if (user!!.notifications.isNullOrEmpty()) {
                        item {
                            Text(
                                    text = "No notifications found",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(8.dp)
                            )
                        }
                    } else {
                        items(user!!.notifications!!) { notification ->
                            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                            text = notification.title,
                                            style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                            text = "Type: ${notification.type}",
                                            style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                            text = "Priority: ${notification.priority}",
                                            style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                            text =
                                                    "Status: ${if (notification.isRead) "Read" else "Unread"}",
                                            style = MaterialTheme.typography.bodySmall
                                    )
                                    notification.createdAt?.let {
                                        Text(
                                                text = "Created: $it",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                            onClick = { onNotificationClick(notification.id) },
                                            modifier = Modifier.fillMaxWidth()
                                    ) { Text("View Details") }
                                }
                            }
                        }
                    }

                    item {
                        Text(
                                text = "Sensor Data (${sensorDataMap.size} devices)",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                    }

                    if (sensorDataMap.isEmpty()) {
                        item {
                            Text(
                                    text = "No sensor data found",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(8.dp)
                            )
                        }
                    } else {
                        items(sensorDataMap.toList()) { (deviceId, sensorData) ->
                            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                            text = "Device: ${sensorData.device.name}",
                                            style = MaterialTheme.typography.titleMedium
                                    )
                                    sensorData.temperature?.let {
                                        Text(
                                                text = "Temperature: $it°C",
                                                style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    sensorData.humidity?.let {
                                        Text(
                                                text = "Humidity: $it%",
                                                style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    sensorData.luminosity?.let {
                                        Text(
                                                text = "Luminosity: $it lux",
                                                style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    sensorData.distance?.let {
                                        Text(
                                                text = "Distance: $it cm",
                                                style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    Text(
                                            text = "Timestamp: ${sensorData.timestamp}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) {
                            Text("Back to Users")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(120.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
