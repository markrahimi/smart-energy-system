package com.cps2.energy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cps2.energy.ui.screens.EditProfileScreen
import com.cps2.energy.ui.screens.NotificationDetailScreen
import com.cps2.energy.ui.screens.NotificationListScreen
import com.cps2.energy.ui.screens.UserDetailScreen
import com.cps2.energy.ui.screens.UserListScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "users") {
        composable("users") {
            UserListScreen(onUserClick = { userId -> navController.navigate("user/$userId") })
        }

        composable(
                route = "user/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            UserDetailScreen(
                    userId = userId,
                    onBackClick = { navController.popBackStack() },
                    onNotificationClick = { notificationId ->
                        navController.navigate("notification/$notificationId")
                    },
                    onEditProfileClick = { id -> navController.navigate("edit-profile/$id") },
                    onViewNotificationsClick = { id -> navController.navigate("notifications/$id") }
            )
        }

        composable(
                route = "edit-profile/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            EditProfileScreen(userId = userId, onBackClick = { navController.popBackStack() })
        }

        composable(
                route = "notifications/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            NotificationListScreen(
                    userId = userId,
                    onNotificationClick = { notificationId ->
                        navController.navigate("notification/$notificationId")
                    },
                    onBackClick = { navController.popBackStack() }
            )
        }

        composable(
                route = "notification/{notificationId}",
                arguments = listOf(navArgument("notificationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val notificationId = backStackEntry.arguments?.getString("notificationId") ?: ""
            NotificationDetailScreen(
                    notificationId = notificationId,
                    onBackClick = { navController.popBackStack() }
            )
        }
    }
}
