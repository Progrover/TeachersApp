package dev.platovco.mentorium.feature.chat.impl.presentation.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import dev.platovco.mentorium.feature.chat.api.ChatFeature
import dev.platovco.mentorium.feature.chat.impl.presentation.components.screencontent.ChatScreenContent
import dev.platovco.mentorium.feature.chat.impl.presentation.contract.chats.ChatUIEffect
import dev.platovco.mentorium.feature.chat.impl.presentation.viewmodel.ChatViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel,
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                ChatUIEffect.NavigateBack ->
                    navController.popBackStack()

                is ChatUIEffect.ShowError ->
                    snackbarHostState.showSnackbar(
                        message = context.getString(effect.messageResId),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )

                is ChatUIEffect.NavigateToCurrentChat ->
                    ChatFeature.openConversationScreen(navController, effect.chatId, effect.name)
            }
        }
    }

    ChatScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
        snackbarHostState = snackbarHostState,
    )
}