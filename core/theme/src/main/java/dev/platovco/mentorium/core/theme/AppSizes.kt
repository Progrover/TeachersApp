package dev.platovco.mentorium.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppSizes(
    val size1: Dp = 1.dp,
    val size5: Dp = 5.dp,
    val size10: Dp = 10.dp,
    val size15: Dp = 15.dp,
    val size20: Dp = 20.dp,
    val size25: Dp = 25.dp,
    val size30: Dp = 30.dp,
    val size35: Dp = 35.dp,
    val size40: Dp = 40.dp,
    val size45: Dp = 45.dp,
    val size50: Dp = 50.dp,
    val size55: Dp = 55.dp,
    val size60: Dp = 60.dp,
    val size65: Dp = 65.dp,
    val size70: Dp = 70.dp,
    val size75: Dp = 75.dp,
    val size80: Dp = 80.dp,
    val size85: Dp = 85.dp,
    val size90: Dp = 90.dp,
    val size95: Dp = 95.dp,
    val size120: Dp = 120.dp,
    val size200: Dp = 200.dp,
    val size220: Dp = 220.dp,
    val size250: Dp = 250.dp,
)

internal val LocalAppSizes = staticCompositionLocalOf { AppSizes() }