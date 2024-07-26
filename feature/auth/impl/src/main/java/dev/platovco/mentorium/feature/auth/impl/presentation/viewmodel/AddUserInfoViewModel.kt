package dev.platovco.mentorium.feature.auth.impl.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.platovco.mentorium.core.base.data.appwrite.AppwriteManager
import dev.platovco.mentorium.core.base.data.storage.Prefs
import dev.platovco.mentorium.core.base.data.storage.SharedPrefs.Companion.PREFS_KEY_REG_STATUS
import dev.platovco.mentorium.core.base.di.CoroutineQualifiers
import dev.platovco.mentorium.core.base.domain.model.AppUser
import dev.platovco.mentorium.core.base.domain.model.RegistrationStatus
import dev.platovco.mentorium.core.base.presentation.viewmodel.BaseViewModel
import dev.platovco.mentorium.core.uicommon.R
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.adduserinfo.AddUserInfoUIEffect
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.adduserinfo.AddUserInfoUIEvent
import dev.platovco.mentorium.feature.auth.impl.presentation.contract.adduserinfo.AddUserInfoUIState
import dev.platovco.mentorium.feature.auth.impl.presentation.model.UserSegmentedButtonVO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class AddUserInfoViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val appwriteManager: AppwriteManager,
    private val contentResolver: ContentResolver,
    private val sharedPrefs: Prefs,
) : BaseViewModel<AddUserInfoUIEvent, AddUserInfoUIState, AddUserInfoUIEffect>(AddUserInfoUIState()) {

    init {
        getArgumentsFromNavigation()
        fetchHintsForTeacher()
    }

    override fun handleUIEvent(event: AddUserInfoUIEvent) =
        when (event) {
            AddUserInfoUIEvent.OnBackClick ->
                handleOnBackClick()

            AddUserInfoUIEvent.OnSaveButtonClick ->
                handleOnSaveButtonClick()

            AddUserInfoUIEvent.OnEducationClick ->
                setState(currentState.copy(
                    isEducationPromptsVisible = !currentState.isEducationPromptsVisible
                ))

            AddUserInfoUIEvent.OnDirectionClick ->
                setState(currentState.copy(
                    isDirectionPromptsVisible = !currentState.isDirectionPromptsVisible
                ))

            is AddUserInfoUIEvent.OnDirectionChanged ->
                setState(currentState.copy(direction = event.text))

            is AddUserInfoUIEvent.OnEducationChanged ->
                setState(currentState.copy(education = event.text))

            is AddUserInfoUIEvent.OnExperienceChanged ->
                handleOnExperienceChanged(event.text)

            is AddUserInfoUIEvent.OnUsernameChanged ->
                handleOnUsernameChanged(event.text)

            is AddUserInfoUIEvent.OnSegmentButtonClick ->
                handleOnSegmentButtonClick(event.button)

            is AddUserInfoUIEvent.OnPictureAdded ->
                handleAddingPicture(event.pictureUri)

            is AddUserInfoUIEvent.OnDirectionPromptClick ->
                handleDirectionPromptClick(event.prompt)

            is AddUserInfoUIEvent.OnEducationPromptClick ->
                handleEducationPromptClick(event.prompt)
        }

    /**
     * Отработка возврата назад. Необходимо выйти из аккаунта во избежание ошибок
     */
    private fun handleOnBackClick() {
        viewModelScope.launch(coroutineExceptionHandler) {
            appwriteManager.logout()
            setEffect(AddUserInfoUIEffect.NavigateBack)
        }
    }

    /**
     * Обработка нажатия на кнопку регистрации пользователя
     */
    private fun handleOnSaveButtonClick() {
        if (hasNoErrors()) {
            viewModelScope.launch(coroutineExceptionHandler) {
                val pictureByteArray = convertImageToByteArray(currentState.uri)
                pictureByteArray?.let { byteArray ->
                    withContext(ioDispatcher) {
                        val json = appwriteManager.uploadImage(byteArray)
                        val url: String = json?.get("url").toString()
                        sharedPrefs.putStatus(PREFS_KEY_REG_STATUS, currentState.selectedStatus)
                        when (currentState.selectedStatus) {
                            RegistrationStatus.Student -> {
                                val user = AppUser(
                                    uuid = "",
                                    name = currentState.userName,
                                    photo = url,
                                    status = RegistrationStatus.Student,
                                )
                                val success = appwriteManager.registerStudent(user)
                                if (success)
                                    setEffect(AddUserInfoUIEffect.NavigateToMainScreen)
                                else loggingUnexpectedBehavior()
                            }

                            RegistrationStatus.Teacher -> {
                                val user = AppUser(
                                    uuid = "",
                                    name = currentState.userName,
                                    photo = url,
                                    direction = currentState.direction,
                                    education = currentState.education,
                                    experience = currentState.experience.toLong(),
                                    status = RegistrationStatus.Teacher,
                                )
                                val success = appwriteManager.registerTeacher(user)
                                if (success)
                                    setEffect(AddUserInfoUIEffect.NavigateToMainScreen)
                                else loggingUnexpectedBehavior()
                            }

                            else ->
                                loggingUnexpectedBehavior()
                        }
                    }
                }
            }
        }
    }

    /**
     * Обработка нажатия на переключатель роли пользователя (студент/преподаватель)
     */
    private fun handleOnSegmentButtonClick(button: UserSegmentedButtonVO) =
        setState(currentState.copy(selectedStatus = button.status))

    /**
     * Отбарботка ввода имени
     */
    private fun handleOnUsernameChanged(text: String) =
        setState(currentState.copy(userName = text))

    /**
     * Отбарботка ввода опыта
     */
    private fun handleOnExperienceChanged(text: String) =
        setState(currentState.copy(experience = text))

    /**
     * Забираем аргументы из навигации. Может прилететь имя пользователя, если входим через Google
     */
    private fun getArgumentsFromNavigation() {
        // TODO реализовать при подключеннии авторизации через Google
    }

    /**
     * Получаем и обрабатываем uri загруженной картинки
     */
    private fun handleAddingPicture(pictureUri: Uri?) {
        pictureUri?.let { uri ->
            setState(currentState.copy(uri = uri))
        }
    }

    /**
     * Метод проверяет ошибки пользоательского ввода
     */
    private fun hasNoErrors(): Boolean {
        setState(
            currentState.copy(
                isUserNameError = false,
                isDirectionError = false,
                isEducationError = false,
                isExperienceError = false,
            )
        )
        if (currentState.userName.isEmpty())
            setState(currentState.copy(
                isUserNameError = true,
                usernameErrorMessageId = R.string.error_field_must_be_nonempty
            ))
        if (currentState.userName.length < 2)
            setState(currentState.copy(
                isUserNameError = true,
                usernameErrorMessageId = R.string.error_name_is_too_short
            ))
        if (currentState.selectedStatus == RegistrationStatus.Teacher) {
            if (currentState.direction.isEmpty())
                setState(currentState.copy(
                    isDirectionError = true,
                    directionErrorMessageId = R.string.error_field_must_be_nonempty
                ))
            if (currentState.direction.length < 4)
                setState(currentState.copy(
                    isDirectionError = true,
                    directionErrorMessageId = R.string.error_direction_is_too_short
                ))
            if (currentState.education.isEmpty())
                setState(currentState.copy(
                    isEducationError = true,
                    educationErrorMessageId = R.string.error_field_must_be_nonempty
                ))
            if (currentState.education.length < 4)
                setState(currentState.copy(
                    isEducationError = true,
                    educationErrorMessageId = R.string.error_education_is_too_short
                ))
            if (currentState.experience.isEmpty())
                setState(currentState.copy(
                    isExperienceError = true,
                    experienceErrorMessageId = R.string.error_field_must_be_nonempty
                ))
        }

        return with (currentState) {
            !(isUserNameError && isDirectionError && isEducationError && isExperienceError)
        }
    }

    /**
     * Конвертация пользовательского изображения в ByteArray
     */
    @SuppressLint("Recycle")
    private fun convertImageToByteArray(uri: Uri?): ByteArray? {
        return try {
            uri?.let {
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                val byteBuffer = ByteArrayOutputStream()

                inputStream?.use { input ->
                    val buffer = ByteArray(1024)
                    var read: Int
                    while (input.read(buffer).also { read = it } != -1) {
                        byteBuffer.write(buffer, 0, read)
                    }
                }

                return byteBuffer.toByteArray()
            }
        } catch (e: Exception) {
            Timber.d("Error while converting image to ByteArray: ${e.message}")
            null
        }
    }

    /**
     * Получаем список подсказок из БД для учительских полей
     */
    private fun fetchHintsForTeacher() {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(ioDispatcher) {
                val educationPrompts = appwriteManager.getEducationPrompts()
                val directionPrompts = appwriteManager.getDirectionPrompts()
                setState(
                    currentState.copy(
                        educationPrompts = educationPrompts,
                        directionPrompts = directionPrompts,
                    )
                )
            }
        }
    }

    /**
     * Обработка нажатия на пункт меню подсказок специальности
     */
    private fun handleEducationPromptClick(prompt: String) {
        setState(
            currentState.copy(
                education = prompt,
                isEducationPromptsVisible = false,
            )
        )
    }

    /**
     * Обработка нажатия на пункт меню подсказок направления
     */
    private fun handleDirectionPromptClick(prompt: String) {
        setState(
            currentState.copy(
                direction = prompt,
                isDirectionPromptsVisible = false,
            )
        )
    }

    /**
     * Метод-заглушка для логироания неожиданного или нежелательного поведения
     */
    private fun loggingUnexpectedBehavior() =
        Timber.d("GreetingVM unexpected behavior")
}