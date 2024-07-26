package dev.platovco.mentorium.feature.auth.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.platovco.mentorium.core.base.presentation.navigation.NavigationFactory
import dev.platovco.mentorium.feature.auth.api.AuthFeature.ADD_USER_INFO_SCREEN
import dev.platovco.mentorium.feature.auth.api.AuthFeature.AUTH_SCREEN
import dev.platovco.mentorium.feature.auth.api.AuthFeature.GREETING_SCREEN
import dev.platovco.mentorium.feature.auth.api.AuthFeature.ROUTE_NAME
import dev.platovco.mentorium.feature.auth.impl.presentation.screen.AddUserInfoScreen
import dev.platovco.mentorium.feature.auth.impl.presentation.screen.AuthScreen
import dev.platovco.mentorium.feature.auth.impl.presentation.screen.GreetingScreen
import dev.platovco.mentorium.feature.auth.impl.presentation.viewmodel.AddUserInfoViewModel
import dev.platovco.mentorium.feature.auth.impl.presentation.viewmodel.AuthViewModel
import dev.platovco.mentorium.feature.auth.impl.presentation.viewmodel.GreetingViewModel
import javax.inject.Inject

class AuthNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = GREETING_SCREEN,
            route = ROUTE_NAME
        ) {

            composable(route = AUTH_SCREEN) {
                val viewModel: AuthViewModel = hiltViewModel()
                AuthScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = GREETING_SCREEN) {
                val viewModel: GreetingViewModel = hiltViewModel()
                GreetingScreen(navController = navController, viewModel = viewModel)
            }

            composable(route = ADD_USER_INFO_SCREEN) {
                val viewModel: AddUserInfoViewModel = hiltViewModel()
                AddUserInfoScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}