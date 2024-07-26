package dev.platovco.mentorium.feature.auth.api

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

object AuthFeature {

    const val ROUTE_NAME = "authFeature"
    const val AUTH_SCREEN = "authScreenRoute"
    const val GREETING_SCREEN = "authGreetingScreen"
    const val ADD_USER_INFO_SCREEN = "addUserInfoScreen"

    fun openAuthScreen(navController: NavController) =
        navController.navigate(AUTH_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
        }

    fun openAddUserInfoScreen(navController: NavController) =
        navController.navigate(ADD_USER_INFO_SCREEN)
}