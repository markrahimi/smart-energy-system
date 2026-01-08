package com.cps2.energy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cps2.energy.data.models.Notification
import com.cps2.energy.viewmodels.NotificationListViewModel

@Composable
fun NotificationListScreen(
        userId: String,
        onNotificationClick: (String) -> Unit,
        onBackClick: () -> Unit,
        viewModel: NotificationListViewModel = viewModel()
) {
    val notifications by viewModel.notifications.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(userId) { viewModel.loadNotifications(userId) }

    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 16.dp)
    ) {
        Text(
                text = "Notifications",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
        )

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
            notifications.isEmpty() -> {
                Box(
                        modifier = Modifier.fillMaxSize().weight(1f),
                        contentAlignment = Alignment.Center
                ) { Text("No notifications") }
            }
            else -> {
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(notifications) { notification ->
                        NotificationCard(
                                notification = notification,
                                onClick = { onNotificationClick(notification.id) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Profile")
        }
    }
}

@Composable
fun NotificationCard(notification: Notification, onClick: () -> Unit) {
    Card(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
            colors =
                    CardDefaults.cardElevation(
                            defaultElevation = if (notification.isRead) 1.dp else 4.dp
                    )
                            .let {
                                if (notification.isRead)
                                        CardDefaults.cardColors()
                                else
                                        CardDefaults.cardColors(
                                                containerColor =
                                                        MaterialTheme.colorScheme.primaryContainer
                                        )
                            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = notification.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                )
                if (!notification.isRead) {
                    Badge(
                            containerColor = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 8.dp)
                    ) { Text("NEW", style = MaterialTheme.typography.labelSmall) }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                        text = "Priority: ${notification.priority}",
                        style = MaterialTheme.typography.bodySmall,
                        color =
                                when (notification.priority) {
                                    "HIGH" -> MaterialTheme.colorScheme.error
                                    "MEDIUM" -> MaterialTheme.colorScheme.tertiary
                                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                                }
                )
                notification.createdAt?.let {
                    Text(
                            text = it.take(16).replace("T", " "),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
