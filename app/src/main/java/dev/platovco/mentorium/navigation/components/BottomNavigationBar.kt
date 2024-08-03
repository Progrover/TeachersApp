package dev.platovco.mentorium.navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.platovco.mentorium.contract.MainUIState
import dev.platovco.mentorium.feature.chat.api.ChatFeature
import dev.platovco.mentorium.feature.joblist.api.JobListFeature
import dev.platovco.mentorium.feature.mainscreen.api.MainScreenFeature
import dev.platovco.mentorium.feature.profile.api.ProfileFeature
import dev.platovco.mentorium.navigation.model.BottomNavigationItem

@Composable
fun BottomNavigationBar(
    uiState: MainUIState,
    navController: NavController,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BottomNavigationBarContent(
        isBottomBarVisible = navBackStackEntry.isBottomBarVisible(uiState.isBottomNavigationBarVisible),
        currentDestination = navBackStackEntry?.destination,
        onClick = { item ->
            when (item) {
                BottomNavigationItem.MainScreen ->
                    MainScreenFeature.openMainScreen(navController)

                BottomNavigationItem.ChatScreen ->
                    ChatFeature.openChatScreen(navController)

                BottomNavigationItem.JobScreen ->
                    JobListFeature.openJobListScreen(navController)

                BottomNavigationItem.ProfileScreen ->
                    ProfileFeature.openProfileScreen(navController)
            }
        }
    )
}

private fun NavBackStackEntry?.isBottomBarVisible(isBottomBarVisible: Boolean): Boolean {
    val screensWhereBottomBarVisible = listOf(
        MainScreenFeature.MAIN_SCREEN,
        JobListFeature.JOBLIST_SCREEN,
        ChatFeature.CHAT_SCREEN,
        ProfileFeature.PROFILE_SCREEN,
    )
    return when (isBottomBarVisible) {
        true -> this?.destination?.route in screensWhereBottomBarVisible
        false -> false
    }
}