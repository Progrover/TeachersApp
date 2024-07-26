package dev.platovco.mentorium.feature.auth.impl.presentation.components.screencontent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.platovco.mentorium.core.base.utils.One
import dev.platovco.mentorium.core.theme.AppTheme
import dev.platovco.mentorium.feature.auth.impl.presentation.model.AuthSegmentedButtonVO
import dev.platovco.mentorium.core.uicommon.view.buttons.DefaultButton
import dev.platovco.mentorium.core.uicommon.view.buttons.SegmentedButtons
import dev.platovco.mentorium.core.uicommon.view.textfields.UniversalOutlinedTextField
import dev.platovco.mentorium.feature.auth.impl.R
import dev.platovco.mentorium.feature.auth.impl.presentation.components.AppLogo
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.auth.AuthUIEvent
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.auth.AuthUIState

@Composable
internal fun AuthScreenContent(
    modifier: Modifier,
    uiState: AuthUIState,
    onEvent: (AuthUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.backgroundVariant),
    ) {

        Column(
            modifier = Modifier
                .padding(top = AppTheme.paddings.paddingX3)
                .statusBarsPadding()
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            AppLogo(
                modifier = Modifier
                    .padding(horizontal = AppTheme.paddings.paddingX3)
            )

            SegmentedButtons(
                modifier = Modifier
                    .padding(horizontal = AppTheme.paddings.paddingX3)
                    .padding(top = AppTheme.paddings.paddingX4),
                buttons = uiState.authButtons,
                onButtonClick = { button ->
                    onEvent(AuthUIEvent.OnSegmentedButtonClick(button as AuthSegmentedButtonVO))
                }
            )

            Column(
                modifier = Modifier
                    .padding(top = AppTheme.paddings.paddingX4)
                    .weight(Float.One)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = AppTheme.corners.cornersX6,
                            topEnd = AppTheme.corners.cornersX6,
                        )
                    )
                    .background(AppTheme.colors.surface)
                    .padding(
                        horizontal = AppTheme.paddings.paddingX3,
                        vertical = AppTheme.paddings.paddingX7
                    ),
                verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.paddingX2),
            ) {

                UniversalOutlinedTextField(
                    title = stringResource(id = R.string.title_email),
                    text = uiState.emailValue,
                    hint = stringResource(id = R.string.hint_email),
                    isErrorState = uiState.isEmailError,
                    errorText = uiState.emailErrorMessageId,
                    onTextChange = { text ->
                        onEvent(AuthUIEvent.OnEmailTextChanged(text))
                    }
                )

                UniversalOutlinedTextField(
                    title = stringResource(id = R.string.title_password),
                    text = uiState.passwordValue,
                    hint = stringResource(id = R.string.hint_password),
                    isErrorState = uiState.isPasswordError,
                    isTextHidden = uiState.isPasswordHidden,
                    errorText = uiState.passwordErrorMessageId,
                    onTextChange = { text ->
                        onEvent(AuthUIEvent.OnPasswordTextChanged(text))
                    }
                )

                AnimatedVisibility(
                    visible = uiState.selectedMode == AuthSegmentedButtonVO.AuthType.Registration,
                    enter = expandVertically(animationSpec = tween(200)) + fadeIn(initialAlpha = 0.3f),
                    exit = shrinkVertically(animationSpec = tween(200)) + fadeOut()
                ) {

                    UniversalOutlinedTextField(
                        title = stringResource(id = R.string.title_password_again),
                        text = uiState.passwordAgainValue,
                        hint = stringResource(id = R.string.hint_passwor_again),
                        isErrorState = uiState.isPasswordAgainError,
                        isTextHidden = uiState.isPasswordAgainHidden,
                        errorText = uiState.passwordAgainErrorMessageId,
                        onTextChange = { text ->
                            onEvent(AuthUIEvent.OnPasswordAgainTextChanged(text))
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(vertical = AppTheme.paddings.paddingX4,)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {

                    HorizontalDivider(
                        modifier = Modifier.width(AppTheme.sizes.size35)
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = AppTheme.paddings.paddingX2),
                        text = stringResource(id = R.string.title_or),
                        style = AppTheme.typography.body3,
                        color = AppTheme.colors.onBackground,
                    )

                    HorizontalDivider(
                        modifier = Modifier.width(AppTheme.sizes.size35)
                    )
                }

                DefaultButton(
                    modifier = Modifier,
                    text = stringResource(id = R.string.title_login_with_google),
                    iconResId = R.drawable.ic_google,
                    onClick = {
                        onEvent(AuthUIEvent.OnGoogleAuthClick)
                    },
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                DefaultButton(
                    modifier = Modifier
                        .navigationBarsPadding(),
                    text = stringResource(id = uiState.buttonText),
                    onClick = {
                        onEvent(AuthUIEvent.OnLoginButtonClick)
                    },
                )
            }
        }
        
        SnackbarHost(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(AppTheme.paddings.paddingX3)
                .align(Alignment.BottomCenter),
            hostState = snackbarHostState,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun AuthScreenContentPreview() =
    AuthScreenContent(
        modifier = Modifier,
        uiState = AuthUIState(),
        onEvent = {},
        snackbarHostState = SnackbarHostState(),
    )