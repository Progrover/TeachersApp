package dev.platovco.mentorium.feature.chat.impl.domain.model

data class ChatItem(
    val chatId: String,
    val picture: String,
    val title: String,
    val lastMessage: String,
    val isLastMessageRidden: Boolean,
)