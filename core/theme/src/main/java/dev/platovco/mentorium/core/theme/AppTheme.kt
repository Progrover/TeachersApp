package dev.platovco.mentorium.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object AppTheme {

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val paddings: AppPaddings
        @Composable
        @ReadOnlyComposable
        get() = LocalAppPaddings.current

    val corners: AppCorners
        @Composable
        @ReadOnlyComposable
        get() = LocalAppCorners.current

    val sizes: AppSizes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppSizes.current
}




@Composable
fun AppThemeComposable(
    darkTheme: Boolean = isSystemInDarkTheme(),
    darkStatusBarIcons: Boolean = !isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colors: AppColors = remember(darkTheme) {
        when (darkTheme) {
            true -> AppColors.DarkColors
            false -> AppColors.LightColors
        }
    }
    val typography: AppTypography = appTypography(TextStyles())

    val systemUiController = rememberSystemUiController()
    val isDarkModeIconsEnabled = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = darkStatusBarIcons,
            isNavigationBarContrastEnforced = false,
        )
    }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        LocalAppPaddings provides AppTheme.paddings,
        LocalAppCorners provides AppTheme.corners,
        LocalAppSizes provides AppTheme.sizes,
    ) {
       content()
    }
}







