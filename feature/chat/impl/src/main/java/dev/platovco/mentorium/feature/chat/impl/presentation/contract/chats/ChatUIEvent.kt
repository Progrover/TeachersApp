package dev.platovco.mentorium.feature.chat.impl.presentation.contract.chats

import dev.platovco.mentorium.core.base.presentation.mvi.UIEvent

sealed class ChatUIEvent : UIEvent {
    data object GoBack : ChatUIEvent()
    data object OnSettingsClick : ChatUIEvent()

    class OnChatClick(val chatId: String, val name: String) : ChatUIEvent()
}