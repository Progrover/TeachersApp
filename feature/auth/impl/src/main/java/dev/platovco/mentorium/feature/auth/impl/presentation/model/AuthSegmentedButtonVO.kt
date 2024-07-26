package dev.platovco.mentorium.feature.auth.impl.presentation.model

import androidx.annotation.StringRes
import dev.platovco.mentorium.core.uicommon.model.SegmentedButton

data class AuthSegmentedButtonVO(
    @StringRes override val titleResId: Int,
    override val isSelected: Boolean,
    val mode: AuthType,
) : SegmentedButton {

    enum class AuthType {
        Auth,
        Registration,
    }
}