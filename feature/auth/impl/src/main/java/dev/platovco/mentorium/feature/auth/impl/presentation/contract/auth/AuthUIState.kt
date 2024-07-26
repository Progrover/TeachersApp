package dev.platovco.mentorium.feature.auth.impl.presentation.contract.auth

import androidx.annotation.StringRes
import dev.platovco.mentorium.core.base.presentation.mvi.UIState
import dev.platovco.mentorium.feature.auth.impl.presentation.model.AuthSegmentedButtonVO
import dev.platovco.mentorium.feature.auth.impl.R
import dev.platovco.mentorium.core.uicommon.R as uiCommonR

data class AuthUIState(
    val isLoading: Boolean = false,
    val selectedMode: AuthSegmentedButtonVO.AuthType = AuthSegmentedButtonVO.AuthType.Registration,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isPasswordAgainError: Boolean = false,
    val emailErrorMessageId: Int = uiCommonR.string.error_field_must_be_nonempty,
    val passwordErrorMessageId: Int = uiCommonR.string.error_field_must_be_nonempty,
    val passwordAgainErrorMessageId: Int = uiCommonR.string.error_field_must_be_nonempty,
    val isPasswordHidden: Boolean = true,
    val isPasswordAgainHidden: Boolean = true,
    val emailValue: String = "",
    val passwordValue: String = "",
    val passwordAgainValue: String = "",
) : UIState {

    val authButtons: List<AuthSegmentedButtonVO> = listOf(
        AuthSegmentedButtonVO(
            titleResId = R.string.title_segmentedbutton_reg,
            isSelected = selectedMode == AuthSegmentedButtonVO.AuthType.Registration,
            mode = AuthSegmentedButtonVO.AuthType.Registration,
        ),
        AuthSegmentedButtonVO(
            titleResId = R.string.title_segmentedbutton_auth,
            isSelected = selectedMode == AuthSegmentedButtonVO.AuthType.Auth,
            mode = AuthSegmentedButtonVO.AuthType.Auth,
        ),
    )

    @StringRes
    val buttonText: Int = when (selectedMode) {
        AuthSegmentedButtonVO.AuthType.Auth -> R.string.title_login
        AuthSegmentedButtonVO.AuthType.Registration -> R.string.title_register
    }
}