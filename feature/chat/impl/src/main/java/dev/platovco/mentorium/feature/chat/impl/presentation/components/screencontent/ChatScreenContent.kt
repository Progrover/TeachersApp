package dev.platovco.mentorium.feature.chat.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.platovco.mentorium.core.theme.AppTheme
import dev.platovco.mentorium.core.uicommon.extensions.bottomNavigationPadding
import dev.platovco.mentorium.core.uicommon.view.topbar.AppToolbar
import dev.platovco.mentorium.feature.chat.impl.R
import dev.platovco.mentorium.feature.chat.impl.presentation.components.ChatListItem
import dev.platovco.mentorium.feature.chat.impl.presentation.contract.chats.ChatUIEvent
import dev.platovco.mentorium.feature.chat.impl.presentation.contract.chats.ChatUIState
import dev.platovco.mentorium.core.uicommon.R as uiCommonR

@Composable
internal fun ChatScreenContent(
    modifier: Modifier,
    uiState: ChatUIState,
    onEvent: (ChatUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .bottomNavigationPadding(),
        ) {

            AppToolbar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = AppTheme.paddings.paddingX3),
                title = stringResource(R.string.title_chat),
                backIconResId = uiCommonR.drawable.ic_arrow_back,
                endIconResId = uiCommonR.drawable.ic_settings,
                iconTint = AppTheme.colors.primary,
                onBackClick = { onEvent(ChatUIEvent.GoBack) },
                onEndIconClick = { onEvent(ChatUIEvent.OnSettingsClick) },
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.paddingX3)
            ) {

                item {
                    Spacer(
                        modifier = Modifier
                            .height(AppTheme.sizes.size15)
                    )
                }

                items(items = uiState.messages, key = { it.chatId }) { item ->
                    ChatListItem(
                        modifier = Modifier,
                        picture = item.picture,
                        title = item.title,
                        message = item.lastMessage,
                        isRidden = item.isLastMessageRidden,
                        onClick = {
                            onEvent(ChatUIEvent.OnChatClick(item.chatId, item.title))
                        }
                    )
                }
            }
        }

        SnackbarHost(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = AppTheme.paddings.paddingX3)
                .bottomNavigationPadding()
                .align(Alignment.BottomCenter),
            hostState = snackbarHostState,
        )
    }
}

@Composable
@Preview(showBackground = true, locale = "ru")
private fun ChatScreenPreview() =
    ChatScreenContent(
        modifier = Modifier,
        uiState = ChatUIState(),
        onEvent = {},
        snackbarHostState = SnackbarHostState(),
    )