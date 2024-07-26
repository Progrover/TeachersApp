package dev.platovco.mentorium.feature.chat.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.platovco.mentorium.core.base.presentation.navigation.NavigationFactory
import dev.platovco.mentorium.feature.chat.api.ChatFeature.CHAT_SCREEN
import dev.platovco.mentorium.feature.chat.api.ChatFeature.CONVERSATION_SCREEN
import dev.platovco.mentorium.feature.chat.api.ChatFeature.ROUTE_NAME
import dev.platovco.mentorium.feature.chat.impl.presentation.screen.ChatScreen
import dev.platovco.mentorium.feature.chat.impl.presentation.screen.ConversationScreen
import dev.platovco.mentorium.feature.chat.impl.presentation.viewmodel.ChatViewModel
import dev.platovco.mentorium.feature.chat.impl.presentation.viewmodel.ConversationViewModel
import javax.inject.Inject

class ChatNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = CHAT_SCREEN,
            route = ROUTE_NAME
        ) {

            composable(route = CHAT_SCREEN) {
                val viewModel: ChatViewModel = hiltViewModel()
                ChatScreen(viewModel = viewModel, navController = navController)
            }

            composable(
                route = CONVERSATION_CONTENT,
                arguments = listOf(
                    navArgument(ARG_KEY_CHAT_ID) { type = NavType.StringType }
                )
            ) {
                val viewModel: ConversationViewModel = hiltViewModel()
                ConversationScreen(navController = navController, viewModel = viewModel)
            }
        }
    }


    companion object {

        internal const val ARG_KEY_CHAT_ID = "argKeyChatId"
        internal const val ARG_KEY_CONVERSATION_TITLE = "argKeyConversationTitle"
        internal const val CONVERSATION_CONTENT = "$CONVERSATION_SCREEN/{$ARG_KEY_CHAT_ID}/{$ARG_KEY_CONVERSATION_TITLE}"
    }
}