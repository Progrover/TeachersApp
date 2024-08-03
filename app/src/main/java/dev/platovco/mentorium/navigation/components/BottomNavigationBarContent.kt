package dev.platovco.mentorium.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import dev.platovco.mentorium.core.theme.AppTheme
import dev.platovco.mentorium.navigation.model.BottomNavigationItem

@Composable
fun BottomNavigationBarContent(
    isBottomBarVisible: Boolean,
    currentDestination: NavDestination?,
    onClick: (BottomNavigationItem) -> Unit,
) {

    AnimatedVisibility(
        visible = isBottomBarVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {

        Surface(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .background(AppTheme.colors.background)
            ) {

                HorizontalDivider(
                    color = AppTheme.colors.containerLow
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.sizes.size55)
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    BottomBarItem(
                        modifier = Modifier
                            .height(AppTheme.sizes.size45),
                        item = BottomNavigationItem.MainScreen,
                        isSelected = currentDestination?.hierarchy
                            ?.any { it.route == BottomNavigationItem.MainScreen.route } == true,
                        onClick = onClick,
                    )

                    BottomBarItem(
                        modifier = Modifier
                            .height(AppTheme.sizes.size45),
                        item = BottomNavigationItem.JobScreen,
                        isSelected = currentDestination?.hierarchy
                            ?.any { it.route == BottomNavigationItem.JobScreen.route } == true,
                        onClick = onClick,
                    )

                    BottomBarItem(
                        modifier = Modifier
                            .height(AppTheme.sizes.size45),
                        item = BottomNavigationItem.ChatScreen,
                        isSelected = currentDestination?.hierarchy
                            ?.any { it.route == BottomNavigationItem.ChatScreen.route } == true,
                        onClick = onClick,
                    )

                    BottomBarItem(
                        modifier = Modifier
                            .height(AppTheme.sizes.size45),
                        item = BottomNavigationItem.ProfileScreen,
                        isSelected = currentDestination?.hierarchy
                            ?.any { it.route == BottomNavigationItem.ProfileScreen.route } == true,
                        onClick = onClick,
                    )
                }
            }
        }
    }
}