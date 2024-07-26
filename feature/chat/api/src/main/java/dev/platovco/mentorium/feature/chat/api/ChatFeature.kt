package dev.platovco.mentorium.feature.chat.api

import androidx.navigation.NavController

object ChatFeature {

    const val ROUTE_NAME = "chatRouteName"
    const val CHAT_SCREEN = "chatScreenName"
    const val CONVERSATION_SCREEN = "conversationScreenName"

    fun openChatScreen(navController: NavController) =
        navController.navigate(CHAT_SCREEN)

    fun openConversationScreen(navController: NavController, chatId: String, pageTitle: String) =
        navController.navigate("$CONVERSATION_SCREEN/$chatId/$pageTitle")
}