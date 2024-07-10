package dev.platovco.mentorium.navigation.host

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import dev.platovco.mentorium.core.base.presentation.navigation.NavigationFactory
import dev.platovco.mentorium.core.theme.AppTheme
import dev.platovco.mentorium.feature.auth.api.AuthFeature
import dev.platovco.mentorium.navigation.components.BottomNavigationBar
import dev.platovco.mentorium.viewmodel.MainActivityViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
internal fun MentoriumNavigation(
    viewModel: MainActivityViewModel,
    bottomSheetNavigator: BottomSheetNavigator,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>,
) {

    val uiState by viewModel.uiState.collectAsState()

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(
            topStart = AppTheme.corners.cornersX4,
            topEnd = AppTheme.corners.cornersX4,
        ),
        sheetBackgroundColor = AppTheme.colors.surface,
        scrimColor = AppTheme.colors.primaryContainer.copy(alpha = 0.4f),
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigationBar(uiState = uiState, navController = navController)
            }
        ) {
            NavHost(navController = navController, startDestination = AuthFeature.ROUTE_NAME) {
                navigationFactories.forEach { factory ->
                    factory.create(this, navController = navController)
                }
            }
        }
    }
}