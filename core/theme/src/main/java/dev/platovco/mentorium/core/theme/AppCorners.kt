package dev.platovco.mentorium.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppCorners(
    val cornersX1: Dp = 5.dp,
    val cornersX2: Dp = 10.dp,
    val cornersX3: Dp = 15.dp,
    val cornersX4: Dp = 20.dp,
    val cornersX6: Dp = 30.dp,
)

internal val LocalAppCorners = staticCompositionLocalOf { AppCorners() }