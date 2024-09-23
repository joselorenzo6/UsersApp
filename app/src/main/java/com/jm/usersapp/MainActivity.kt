package com.jm.usersapp

import UserList
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jm.ui.navigation.AppNavigation
import com.jm.ui.screen.UserScreen
import com.jm.ui.viewmodel.UsersViewmodel
import com.jm.usersapp.ui.theme.UsersAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewmodel: UsersViewmodel by viewModels<UsersViewmodel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UsersAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = AppNavigation.NavigationItem.Users.route
                ) {
                    composable(AppNavigation.NavigationItem.Users.route) { UserList(navController,viewmodel) }
                    composable(AppNavigation.NavigationItem.User.route) { UserScreen(navController, viewmodel) }
                }

            }
        }
    }
}


