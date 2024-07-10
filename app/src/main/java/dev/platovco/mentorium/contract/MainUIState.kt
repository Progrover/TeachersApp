package dev.platovco.mentorium.contract

import dev.platovco.mentorium.core.base.presentation.mvi.UIState

data class MainUIState(
    val isBottomNavigationBarVisible: Boolean = true,
) : UIState