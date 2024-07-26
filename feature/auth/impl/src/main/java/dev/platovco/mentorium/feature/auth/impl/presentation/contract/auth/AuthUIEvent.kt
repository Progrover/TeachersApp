package dev.platovco.mentorium.feature.auth.impl.presentation.contract.auth

import dev.platovco.mentorium.core.base.presentation.mvi.UIEvent
import dev.platovco.mentorium.feature.auth.impl.presentation.model.AuthSegmentedButtonVO

sealed class AuthUIEvent : UIEvent {
    data object OnLoginButtonClick : AuthUIEvent()
    data object OnGoogleAuthClick : AuthUIEvent()

    class OnSegmentedButtonClick(val segmentedButton: AuthSegmentedButtonVO) : AuthUIEvent()
    class OnEmailTextChanged(val text: String) : AuthUIEvent()
    class OnPasswordTextChanged(val text: String) : AuthUIEvent()
    class OnPasswordAgainTextChanged(val text: String) : AuthUIEvent()
}