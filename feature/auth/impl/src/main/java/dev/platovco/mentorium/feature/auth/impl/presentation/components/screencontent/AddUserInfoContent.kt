package dev.platovco.mentorium.feature.auth.impl.presentation.components.screencontent

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.platovco.mentorium.core.base.domain.model.RegistrationStatus
import dev.platovco.mentorium.core.base.utils.One
import dev.platovco.mentorium.core.theme.AppTheme
import dev.platovco.mentorium.core.uicommon.view.buttons.DefaultButton
import dev.platovco.mentorium.core.uicommon.view.buttons.SegmentedButtons
import dev.platovco.mentorium.core.uicommon.view.textfields.UniversalOutlinedTextField
import dev.platovco.mentorium.core.uicommon.view.topbar.AppToolbar
import dev.platovco.mentorium.feature.auth.impl.R
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.adduserinfo.AddUserInfoUIEvent
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.adduserinfo.AddUserInfoUIState
import dev.platovco.mentorium.feature.auth.impl.presentation.model.UserSegmentedButtonVO
import dev.platovco.mentorium.core.uicommon.R as uiCommonR

@Composable
internal fun AddUserInfoScreenContent(
    modifier: Modifier,
    uiState: AddUserInfoUIState,
    onEvent: (AddUserInfoUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onEvent(AddUserInfoUIEvent.OnPictureAdded(uri))
    }

    BackHandler {
        onEvent(AddUserInfoUIEvent.OnBackClick)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(
                        bottomStart = AppTheme.corners.cornersX4,
                        bottomEnd = AppTheme.corners.cornersX4,
                    ))
                    .background(AppTheme.colors.backgroundVariant)
                    .padding(horizontal = AppTheme.paddings.paddingX3)
                    .padding(bottom = AppTheme.paddings.paddingX3)
            ) {

                AppToolbar(
                    modifier = Modifier
                        .statusBarsPadding(),
                    title = stringResource(id = R.string.title_profile_data),
                    backgroundColor = AppTheme.colors.backgroundVariant,
                    iconTint = AppTheme.colors.onBackgroundVariant,
                    onBackClick = {
                        onEvent(AddUserInfoUIEvent.OnBackClick)
                    }
                )

                SegmentedButtons(
                    modifier = Modifier
                        .fillMaxWidth(),
                    buttons = uiState.segmentedButtons,
                    onButtonClick = { button ->
                        onEvent(AddUserInfoUIEvent.OnSegmentButtonClick(button as UserSegmentedButtonVO))
                    }
                )
            }

                Column(
                    modifier = Modifier
                        .padding(top = AppTheme.paddings.paddingX6)
                        .padding(horizontal = AppTheme.paddings.paddingX3)
                        .navigationBarsPadding()
                        .padding(bottom = AppTheme.paddings.paddingX3),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.paddingX4)
                ) {

                    Box(
                        contentAlignment = Alignment.BottomEnd
                    ) {

                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(AppTheme.corners.cornersX2))
                                .background(AppTheme.colors.containerHighest)
                                .clickable { launcher.launch("image/*") },
                            painter = rememberAsyncImagePainter(model = uiState.uri),
                            contentDescription = null,
                            contentScale = Crop,
                        )

                        Box(
                            modifier = Modifier
                                .padding(
                                    horizontal = AppTheme.paddings.paddingX6,
                                    vertical = AppTheme.paddings.paddingX3
                                )
                                .size(AppTheme.sizes.size60)
                                .clip(CircleShape)
                                .background(AppTheme.colors.accent)
                                .clickable { launcher.launch("image/*") },
                            contentAlignment = Alignment.Center,
                        ) {

                            Image(
                                modifier = Modifier
                                    .size(AppTheme.sizes.size30),
                                imageVector = ImageVector.vectorResource(uiCommonR.drawable.ic_camera),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.White),
                            )
                        }
                    }

                    UniversalOutlinedTextField(
                        modifier = Modifier,
                        title = stringResource(id = R.string.title_name),
                        text = uiState.userName,
                        hint = stringResource(id = R.string.hint_name),
                        isErrorState = uiState.isUserNameError,
                        errorText = uiState.usernameErrorMessageId,
                        onTextChange = { text ->
                            onEvent(AddUserInfoUIEvent.OnUsernameChanged(text))
                        }
                    )

                    AnimatedVisibility(visible = uiState.selectedStatus == RegistrationStatus.Teacher) {
                            UniversalOutlinedTextField(
                                modifier = Modifier
                                    .clickable { onEvent(AddUserInfoUIEvent.OnDirectionClick) },
                                title = stringResource(id = R.string.title_direction),
                                text = uiState.direction,
                                hint = stringResource(id = R.string.hint_direction),
                                isErrorState = uiState.isDirectionError,
                                errorText = uiState.directionErrorMessageId,
                                prompts = uiState.directionPrompts,
                                isPromptsVisible = uiState.isDirectionPromptsVisible,
                                isEnabled = false,
                                onTextChange = { text ->
                                    onEvent(AddUserInfoUIEvent.OnDirectionChanged(text))
                                },
                                onPromptClick = { prompt ->
                                    onEvent(AddUserInfoUIEvent.OnDirectionPromptClick(prompt))
                                }
                            )
                    }

                    AnimatedVisibility(visible = uiState.selectedStatus == RegistrationStatus.Teacher) {
                        UniversalOutlinedTextField(
                            modifier = Modifier
                                .clickable { onEvent(AddUserInfoUIEvent.OnEducationClick) },
                            title = stringResource(id = R.string.title_eucation),
                            text = uiState.education,
                            hint = stringResource(id = R.string.hint_education),
                            isErrorState = uiState.isEducationError,
                            errorText = uiState.educationErrorMessageId,
                            prompts = uiState.educationPrompts,
                            isPromptsVisible = uiState.isEducationPromptsVisible,
                            isEnabled = false,
                            onTextChange = { text ->
                                onEvent(AddUserInfoUIEvent.OnEducationChanged(text))
                            },
                            onPromptClick = { prompt ->
                                onEvent(AddUserInfoUIEvent.OnEducationPromptClick(prompt))
                            }
                        )
                    }

                    AnimatedVisibility(visible = uiState.selectedStatus == RegistrationStatus.Teacher) {
                        UniversalOutlinedTextField(
                            modifier = Modifier,
                            title = stringResource(id = R.string.title_experience),
                            text = uiState.experience,
                            hint = stringResource(id = R.string.hint_experience),
                            isErrorState = uiState.isExperienceError,
                            errorText = uiState.experienceErrorMessageId,
                            onTextChange = { text ->
                                onEvent(AddUserInfoUIEvent.OnExperienceChanged(text))
                            }
                        )
                    }

                    Spacer(
                        modifier = Modifier.weight(Float.One)
                    )

                    DefaultButton(
                        modifier = Modifier,
                        text = stringResource(id = R.string.title_save_and_continue),
                        onClick = {
                            onEvent(AddUserInfoUIEvent.OnSaveButtonClick)
                        }
                    )
            }
        }

        SnackbarHost(
            modifier = Modifier
                .padding(bottom = AppTheme.paddings.paddingX3)
                .navigationBarsPadding()
                .align(Alignment.BottomCenter),
            hostState = snackbarHostState,
        )
    }
}

@Composable
@Preview(showBackground = true, locale = "ru")
private fun AddUserInfoScreenContentPreview() =
    AddUserInfoScreenContent(
        modifier = Modifier,
        uiState = AddUserInfoUIState(selectedStatus = RegistrationStatus.Teacher),
        onEvent = {},
        snackbarHostState = SnackbarHostState(),
    )