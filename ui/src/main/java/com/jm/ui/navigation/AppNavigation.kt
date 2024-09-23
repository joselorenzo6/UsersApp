package com.jm.ui.navigation

class AppNavigation {
    enum class Screen {
        USERS,
        USER,
    }

    sealed class NavigationItem(val route: String) {
        object Users : NavigationItem(Screen.USERS.name)
        object User : NavigationItem("${Screen.USER.name}/{user}") {
            fun createRoute(user: String): String {
                return "${Screen.USER.name}/$user"
            }

        }
    }

}