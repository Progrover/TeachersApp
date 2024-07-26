package dev.platovco.mentorium.feature.chat.impl.presentation.contract.chats

import androidx.annotation.StringRes
import dev.platovco.mentorium.core.base.presentation.mvi.UIEffect

sealed class ChatUIEffect : UIEffect {
    data object NavigateBack : ChatUIEffect()

    class ShowError(@StringRes val messageResId: Int) : ChatUIEffect()
    class NavigateToCurrentChat(val chatId: String, val name: String) : ChatUIEffect()
}