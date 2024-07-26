package dev.platovco.mentorium.feature.auth.impl.presentation.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import dev.platovco.mentorium.feature.auth.api.AuthFeature
import dev.platovco.mentorium.feature.auth.impl.presentation.components.screencontent.AuthScreenContent
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.auth.AuthUIEffect
import dev.platovco.mentorium.feature.auth.impl.presentation.viewmodel.AuthViewModel
import dev.platovco.mentorium.feature.mainscreen.api.MainScreenFeature
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel,
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                AuthUIEffect.NavigateToAddUserInfoScreen ->
                    AuthFeature.openAddUserInfoScreen(navController)

                AuthUIEffect.NavigateToMainScreen ->
                    MainScreenFeature.openMainScreen(navController)

                is AuthUIEffect.ShowError ->
                    snackbarHostState.showSnackbar(
                        message = context.getString(effect.messageResId),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
            }
        }
    }

    AuthScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
        snackbarHostState = snackbarHostState,
    )
}