package dev.platovco.mentorium.feature.auth.impl.presentation.contract.adduserinfo

import android.net.Uri
import dev.platovco.mentorium.core.base.presentation.mvi.UIEvent
import dev.platovco.mentorium.feature.auth.impl.presentation.model.UserSegmentedButtonVO

sealed class AddUserInfoUIEvent : UIEvent {
    data object OnBackClick : AddUserInfoUIEvent()
    data object OnSaveButtonClick : AddUserInfoUIEvent()
    data object OnEducationClick : AddUserInfoUIEvent()
    data object OnDirectionClick : AddUserInfoUIEvent()

    class OnSegmentButtonClick(val button: UserSegmentedButtonVO) : AddUserInfoUIEvent()
    class OnUsernameChanged(val text: String) : AddUserInfoUIEvent()
    class OnDirectionChanged(val text: String) : AddUserInfoUIEvent()
    class OnEducationChanged(val text: String) : AddUserInfoUIEvent()
    class OnExperienceChanged(val text: String) : AddUserInfoUIEvent()
    class OnPictureAdded(val pictureUri: Uri?) : AddUserInfoUIEvent()
    class OnDirectionPromptClick(val prompt: String) : AddUserInfoUIEvent()
    class OnEducationPromptClick(val prompt: String) : AddUserInfoUIEvent()
}