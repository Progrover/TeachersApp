package dev.platovco.mentorium.feature.auth.impl.presentation.contract.adduserinfo

import androidx.annotation.StringRes
import dev.platovco.mentorium.core.base.presentation.mvi.UIEffect

sealed class AddUserInfoUIEffect : UIEffect {
    data object NavigateBack : AddUserInfoUIEffect()
    data object NavigateToMainScreen : AddUserInfoUIEffect()

    class ShowError(@StringRes val messageResId: Int) : AddUserInfoUIEffect()
}