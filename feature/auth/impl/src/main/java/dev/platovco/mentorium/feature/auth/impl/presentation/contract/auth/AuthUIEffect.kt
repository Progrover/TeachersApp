package dev.platovco.mentorium.feature.auth.impl.presentation.contract.auth

import androidx.annotation.StringRes
import dev.platovco.mentorium.core.base.presentation.mvi.UIEffect

sealed class AuthUIEffect : UIEffect {
    data object NavigateToAddUserInfoScreen : AuthUIEffect()
    data object NavigateToMainScreen : AuthUIEffect()

    class ShowError(@StringRes val messageResId: Int) : AuthUIEffect()
}