package dev.platovco.mentorium.feature.auth.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.platovco.mentorium.core.base.data.appwrite.AppwriteManager
import dev.platovco.mentorium.core.base.data.storage.Prefs
import dev.platovco.mentorium.core.base.data.storage.SharedPrefs.Companion.PREFS_KEY_REG_STATUS
import dev.platovco.mentorium.core.base.di.CoroutineQualifiers
import dev.platovco.mentorium.core.base.domain.model.UserStatus
import dev.platovco.mentorium.core.base.presentation.viewmodel.BaseViewModel
import dev.platovco.mentorium.feature.auth.impl.presentation.model.AuthSegmentedButtonVO
import dev.platovco.mentorium.feature.auth.impl.R
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.auth.AuthUIEffect
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.auth.AuthUIEvent
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.auth.AuthUIState
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.greeting.GreetingUIEffect
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import dev.platovco.mentorium.core.uicommon.R as uiCommonR
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val appwriteManager: AppwriteManager,
    private val sharedPrefs: Prefs,
) : BaseViewModel<AuthUIEvent, AuthUIState, AuthUIEffect>(AuthUIState()) {

    override fun handleUIEvent(event: AuthUIEvent) =
        when (event) {
            AuthUIEvent.OnLoginButtonClick ->
                handleLoginButtonClick()

            AuthUIEvent.OnGoogleAuthClick ->
                setEffect(AuthUIEffect.ShowError(R.string.error_not_implemented))

            is AuthUIEvent.OnSegmentedButtonClick ->
                handleOnSegmentedButtonClick(event.segmentedButton)

            is AuthUIEvent.OnEmailTextChanged ->
                handleEmailTextChanges(event.text)

            is AuthUIEvent.OnPasswordTextChanged ->
                handlePasswordTextChanges(event.text)

            is AuthUIEvent.OnPasswordAgainTextChanged ->
                handlePasswordAgainTextChanges(event.text)
        }

    /**
     * Отработка нажатий на одну из сегментых кнопок (регистрация/авторизация)
     */
    private fun handleOnSegmentedButtonClick(button: AuthSegmentedButtonVO) =
        setState(
            currentState.copy(
                selectedMode = button.mode
            )
        )

    /**
     * Обработка изменения текста в поле email
     */
    private fun handleEmailTextChanges(text: String) =
        setState(
            currentState.copy(
                emailValue = text
            )
        )

    /**
     * Обработка изменения текста в поле пароля
     */
    private fun handlePasswordTextChanges(text: String) =
        setState(
            currentState.copy(
                passwordValue = text
            )
        )

    /**
     * Обработка изменения текста в поле повторного ввода пароля
     */
    private fun handlePasswordAgainTextChanges(text: String) =
        setState(
            currentState.copy(
                passwordAgainValue = text
            )
        )

    /**
     * Обработка нажатия на кнопку входа или регистрации
     */
    private fun handleLoginButtonClick() {
        if (hasNoErrors() && currentState.selectedMode == AuthSegmentedButtonVO.AuthType.Registration) {
            setState(currentState.copy(isLoading = true))
            viewModelScope.launch(coroutineExceptionHandler) {
                withContext(ioDispatcher) {
                    val success: Boolean = appwriteManager.registerAccount(
                        currentState.emailValue,
                        currentState.passwordValue,
                    )
                    Timber.d("success registration = $success")
                    setState(currentState.copy(isLoading = false))
                    when (success) {
                        true -> setEffect(AuthUIEffect.NavigateToAddUserInfoScreen)
                        false -> setEffect(AuthUIEffect.ShowError(R.string.error_something_went_wrong))
                    }
                }
            }
        } else if (hasNoErrors() && currentState.selectedMode == AuthSegmentedButtonVO.AuthType.Auth) {
            setState(currentState.copy(isLoading = true))
            viewModelScope.launch(coroutineExceptionHandler) {
                withContext(ioDispatcher) {
                    val success: Boolean = appwriteManager.login(
                        currentState.emailValue,
                        currentState.passwordValue,
                    )
                    Timber.d("success login = $success")
                    if (success) {
                        val userInfo = appwriteManager.getUserStatus()
                        sharedPrefs.putStatus(PREFS_KEY_REG_STATUS, userInfo.second)
                        setState(currentState.copy(isLoading = false))
                        handleUserStatus(userInfo.first)
                    }
                    else
                        setEffect(AuthUIEffect.ShowError(R.string.error_something_went_wrong))
                }
            }
        }
    }

    /**
     * Метод проверяет ошибки пользоательского ввода
     */
    private fun hasNoErrors(): Boolean {
        setState(
            currentState.copy(
                isEmailError = false,
                isPasswordError = false,
                isPasswordAgainError = false,
            )
        )
        if (currentState.emailValue.isEmpty())
            setState(currentState.copy(
                isEmailError = true,
                emailErrorMessageId = uiCommonR.string.error_field_must_be_nonempty
            ))
        if (!currentState.emailValue.contains('@'))
            setState(currentState.copy(
                isEmailError = true,
                emailErrorMessageId = uiCommonR.string.error_invalid_email_format
            ))
        if (currentState.passwordValue.isEmpty())
            setState(currentState.copy(
                isPasswordError = true,
                passwordErrorMessageId = uiCommonR.string.error_field_must_be_nonempty
            ))
        if (currentState.passwordAgainValue.isEmpty())
            setState(currentState.copy(
                isPasswordAgainError = true,
                passwordAgainErrorMessageId = uiCommonR.string.error_field_must_be_nonempty
            ))
        if (currentState.passwordValue.length < 8)
            setState(currentState.copy(
                isPasswordError = true,
                passwordErrorMessageId = uiCommonR.string.error_passwords_is_too_short
            ))
        if (currentState.emailValue.length < 6)
            setState(currentState.copy(
                isEmailError = true,
                emailErrorMessageId = uiCommonR.string.error_email_is_too_short
            ))
        if ((currentState.passwordValue != currentState.passwordAgainValue) &&
            currentState.selectedMode == AuthSegmentedButtonVO.AuthType.Registration
            )
            setState(currentState.copy(
                isPasswordAgainError = true,
                passwordAgainErrorMessageId = uiCommonR.string.error_passwords_must_be_the_same
            ))

        return with (currentState) {
            !(isEmailError && isPasswordError && isPasswordAgainError)
        }
    }

    /**
     * Направляем пользователя в зависимости от результата проверки.
     */
    private fun handleUserStatus(userStatus: UserStatus) =
        // TODO проработать эту логику после добавления нормальной верификации
        when (userStatus) {
            UserStatus.Unregistered,
            UserStatus.Unverified ->
                loggingUnexpectedBehavior()

            UserStatus.VerifiedWithoutInfo ->
                setEffect(AuthUIEffect.NavigateToAddUserInfoScreen)

            UserStatus.Verified ->
                setEffect(AuthUIEffect.NavigateToMainScreen)
        }

    /**
     * Метод-заглушка для логироания неожиданного или нежелательного поведения
     */
    private fun loggingUnexpectedBehavior() =
        Timber.d("GreetingVM unexpected behavior")
}