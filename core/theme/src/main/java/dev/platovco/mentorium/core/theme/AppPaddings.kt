package dev.platovco.mentorium.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppPaddings(
    val extraSmall: Dp = 3.dp,
    val paddingX1: Dp = 5.dp,
    val paddingX2: Dp = 10.dp,
    val paddingX3: Dp = 15.dp,
    val paddingX4: Dp = 20.dp,
    val paddingX5: Dp = 25.dp,
    val paddingX6: Dp = 30.dp,
    val paddingX7: Dp = 35.dp,
    val paddingX9: Dp = 45.dp,
    val paddingX10: Dp = 50.dp,
    val paddingX11: Dp = 55.dp,
    val paddingX14: Dp = 70.dp,
)

internal val LocalAppPaddings = staticCompositionLocalOf { AppPaddings() }