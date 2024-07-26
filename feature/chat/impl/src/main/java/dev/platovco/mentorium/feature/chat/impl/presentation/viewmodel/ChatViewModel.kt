package dev.platovco.mentorium.feature.chat.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.platovco.mentorium.core.base.data.appwrite.AppwriteManager
import dev.platovco.mentorium.core.base.di.CoroutineQualifiers
import dev.platovco.mentorium.core.base.presentation.viewmodel.BaseViewModel
import dev.platovco.mentorium.feature.chat.impl.presentation.contract.chats.ChatUIEffect
import dev.platovco.mentorium.feature.chat.impl.presentation.contract.chats.ChatUIEvent
import dev.platovco.mentorium.feature.chat.impl.presentation.contract.chats.ChatUIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import dev.platovco.mentorium.core.uicommon.R as uiCommonR
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val appwriteManager: AppwriteManager,
): BaseViewModel<ChatUIEvent, ChatUIState, ChatUIEffect>(ChatUIState())  {

    override fun handleUIEvent(event: ChatUIEvent) =
        when (event) {
            ChatUIEvent.GoBack ->
                setEffect(ChatUIEffect.NavigateBack)

            ChatUIEvent.OnSettingsClick ->
                setEffect(ChatUIEffect.ShowError(uiCommonR.string.error_not_implemented))

            is ChatUIEvent.OnChatClick ->
                setEffect(ChatUIEffect.NavigateToCurrentChat(event.chatId, event.name))
        }
}