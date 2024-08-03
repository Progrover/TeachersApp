package dev.platovco.mentorium.navigation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import dev.platovco.mentorium.core.theme.AppTheme
import dev.platovco.mentorium.navigation.model.BottomNavigationItem

@Composable
fun RowScope.BottomBarItem(
    modifier: Modifier,
    item: BottomNavigationItem,
    isSelected: Boolean,
    onClick: (BottomNavigationItem) -> Unit,
    selectedColor: Color = Color.White,
    unselectedColor: Color = AppTheme.colors.primary,
) {

    val backgroundColor = when (isSelected) {
        true -> AppTheme.colors.primary
        false -> Color.Transparent
    }

    NavigationBarItem(
        modifier = modifier
            .padding(horizontal = AppTheme.paddings.paddingX1)
            .clip(RoundedCornerShape(AppTheme.corners.cornersX1))
            .background(backgroundColor),
        icon = {

            Image(
                modifier = Modifier
                    .size(AppTheme.sizes.size25),
                painter = painterResource(item.iconResId),
                contentDescription = null,
                colorFilter = when (isSelected) {
                    true -> ColorFilter.tint(selectedColor)
                    false -> ColorFilter.tint(unselectedColor)
                }
            )
        },
        interactionSource = remember { MutableInteractionSource() },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = selectedColor,
            unselectedIconColor = unselectedColor,
            indicatorColor = backgroundColor,
        ),
        selected = isSelected,
        onClick = { onClick(item) },
    )
}