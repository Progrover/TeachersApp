package dev.platovco.mentorium.feature.auth.impl.presentation.contract.adduserinfo

import android.net.Uri
import dev.platovco.mentorium.core.base.domain.model.RegistrationStatus
import dev.platovco.mentorium.core.base.presentation.mvi.UIState
import dev.platovco.mentorium.core.uicommon.R as uiCommonR
import dev.platovco.mentorium.feature.auth.impl.R
import dev.platovco.mentorium.feature.auth.impl.presentation.model.UserSegmentedButtonVO

data class AddUserInfoUIState(
    val selectedStatus: RegistrationStatus = RegistrationStatus.Student,
    val avatarURI: String = "",
    val uri: Uri? = null,
    val userName: String = "",
    val direction: String = "",
    val education: String = "",
    val experience: String = "",
    val isUserNameError: Boolean = false,
    val isDirectionError: Boolean = false,
    val isEducationError: Boolean = false,
    val isExperienceError: Boolean = false,
    val usernameErrorMessageId: Int = uiCommonR.string.error_field_must_be_nonempty,
    val directionErrorMessageId: Int = uiCommonR.string.error_field_must_be_nonempty,
    val educationErrorMessageId: Int = uiCommonR.string.error_field_must_be_nonempty,
    val experienceErrorMessageId: Int = uiCommonR.string.error_field_must_be_nonempty,
    val educationPrompts: List<String> = emptyList(),
    val directionPrompts: List<String> = emptyList(),
    val isEducationPromptsVisible: Boolean = false,
    val isDirectionPromptsVisible: Boolean = false,
) : UIState {

    val segmentedButtons: List<UserSegmentedButtonVO> = listOf(
        UserSegmentedButtonVO(
            titleResId = R.string.title_i_am_student,
            isSelected = selectedStatus == RegistrationStatus.Student,
            status = RegistrationStatus.Student,
        ),
        UserSegmentedButtonVO(
            titleResId = R.string.title_i_am_teacher,
            isSelected = selectedStatus == RegistrationStatus.Teacher,
            status = RegistrationStatus.Teacher,
        ),
    )
}