package dev.platovco.mentorium.core.base.data.appwrite

import dev.platovco.mentorium.core.base.domain.model.AppUser
import dev.platovco.mentorium.core.base.domain.model.RegistrationStatus
import dev.platovco.mentorium.core.base.domain.model.UserStatus
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.services.Functions
import org.json.JSONObject
import timber.log.Timber
import java.util.Base64
import java.util.UUID

open class AppwriteManager(
    private val functions: Functions,
    private val databases: Databases,
    private val account: Account,
) {

    /**
     * Регистрация студента
     */
    suspend fun registerStudent(appUser: AppUser): Boolean {
        return try {
            databases.createDocument(
                databaseId = REPETITOR_DATABASE_ID,
                collectionId = COLLECTION_STUDENTS,
                documentId = account.get().id,
                data = mapOf(
                    "name" to appUser.name,
                    "photo" to appUser.photo,
                )
            )
            true
        } catch (e: Exception) {
            logger(e)
            false
        }
    }

    /**
     * Регистрация преподавателя
     */
    suspend fun registerTeacher(appUser: AppUser): Boolean {
        return try {
            databases.createDocument(
                databaseId = REPETITOR_DATABASE_ID,
                collectionId = COLLECTION_TEACHERS,
                documentId = account.get().id,
                data = mapOf(
                    "name" to appUser.name,
                    "photo" to appUser.photo,
                    "direction" to appUser.direction,
                    "education" to appUser.education,
                    "experience" to appUser.experience
                )
            )
            true
        } catch (e: Exception) {
            logger(e)
            false
        }
    }

    /**
     * Регистрация глобального аккаунта (не пользователя)
     */
    suspend fun registerAccount(email: String, password: String): Boolean {
        return try {
            account.create(
                userId = UUID.randomUUID().toString(),
                email = email,
                password = password,
                name = email
            )
            login(email, password)
            true
        } catch (e: Exception) {
            logger(e)
            false
        }
    }

    /**
     * Проверка наличия открытой сессии
     */
    suspend fun checkSessionExists(): Boolean =
        try {
            account.get()
            true
        } catch (e: Exception) { false }

    /**
     * Проверка статуса глобального аккааунта (не пользователя)
     */
    suspend fun getUserStatus(): Pair<UserStatus, RegistrationStatus> {
        //TODO раскомментировать этот код, когда появится функция для email-верификации
//        if (!account.get().emailVerification)
//            return Pair(UserStatus.Unverified, RegistrationStatus.Unknown)

        try {
            Timber.d("Account id: ${account.get().id}")
            databases.getDocument(
                databaseId = REPETITOR_DATABASE_ID,
                collectionId = COLLECTION_STUDENTS,
                documentId = account.get().id,
            )
            return Pair(UserStatus.Verified, RegistrationStatus.Student)
        } catch (e: Exception) { logger(e) }

        try {
            databases.getDocument(
                databaseId = REPETITOR_DATABASE_ID,
                collectionId = COLLECTION_STUDENTS,
                documentId = account.get().id,
            )
            return Pair(UserStatus.Verified, RegistrationStatus.Teacher)
        } catch (e: Exception) { logger(e) }

        return Pair(UserStatus.VerifiedWithoutInfo, RegistrationStatus.Unknown)
    }

    /**
     * Загрузка картинок на сервер
     */
    suspend fun uploadImage(file: ByteArray): JSONObject? {
        return try {
            val jwt = account.createJWT()
            val body = JSONObject()
            body.apply {
                put("file", Base64.getEncoder().encodeToString(file))
                put("jwt", jwt.jwt)
            }
            val request = functions.createExecution(
                functionId = FUNCTION_UPLOAD_IMAGE,
                body = body.toString(),
            )
            JSONObject(request.responseBody)
        } catch (e: Exception) {
            logger(e)
            null
        }
    }

    /**
     * Выход из аккаунта
     */
    suspend fun logout() =
        account.deleteSessions()

    /**
     * логиним аккаунт
     */
    suspend fun login(email: String, password: String): Boolean {
        return try {
            account.createEmailPasswordSession(
                email = email,
                password = password,
            )
            true
        } catch (e: Exception) {
            logger(e)
            false
        }
    }

    /**
     * Получить всех пользователей (студент получит учителей, учитель студентов)
     */
    suspend fun getAllRequiredUsers(myStatus: RegistrationStatus): List<AppUser> {
        return try {
            when (myStatus) {
                RegistrationStatus.Teacher -> {
                    val documents = databases.listDocuments(
                        databaseId = REPETITOR_DATABASE_ID,
                        collectionId = COLLECTION_STUDENTS,
                    ).documents
                    documents.map {
                        AppUser(
                            uuid = it.id,
                            name = it.data["name"] as String,
                            photo = it.data["photo"] as String,
                            status = RegistrationStatus.Student,
                        )
                    }
                }

                RegistrationStatus.Student -> {
                    val documents = databases.listDocuments(
                        databaseId = REPETITOR_DATABASE_ID,
                        collectionId = COLLECTION_TEACHERS,
                    ).documents
                    documents.map {
                        AppUser(
                            uuid = it.id,
                            name = it.data["name"] as String,
                            photo = it.data["photo"] as String,
                            direction = it.data["direction"] as String,
                            education = it.data["education"] as String,
                            experience = it.data["experience"] as Long,
                            status = RegistrationStatus.Teacher,
                        )
                    }
                }

                RegistrationStatus.Unknown ->
                    emptyList()
            }
        } catch (e: Exception) {
            logger(e)
            emptyList()
        }
    }

    /**
     * Получаем подсказки для учительского поля "направление"
     */
    suspend fun getDirectionPrompts(): List<String> =
        try {
            databases.listDocuments(
                databaseId = REPETITOR_DATABASE_ID,
                collectionId = COLLECTION_DIRECTION,
            ).documents.map { it.data["direction"] as String }
        } catch (e: Exception) {
            logger(e)
            emptyList()
        }

    /**
     * Получаем подсказки для учительского поля "образование"
     */
    suspend fun getEducationPrompts(): List<String> =
        try {
            databases.listDocuments(
                databaseId = REPETITOR_DATABASE_ID,
                collectionId = COLLECTION_EDUCATION,
            ).documents.map { it.data["vuz"] as String }
        } catch (e: Exception) {
            logger(e)
            emptyList()
        }

    /**
     * Получение профиля пользователя
     */
    suspend fun getUserProfile(uuid: String, status: RegistrationStatus): AppUser? {
        try {
            when (status) {
                RegistrationStatus.Teacher -> {
                    val document = databases.getDocument(
                        databaseId = REPETITOR_DATABASE_ID,
                        collectionId = COLLECTION_TEACHERS,
                        documentId = uuid
                    )
                    return AppUser(
                        uuid = document.id,
                        name = document.data["name"] as String,
                        photo = document.data["photo"] as String,
                        direction = document.data["direction"] as String,
                        education = document.data["education"] as String,
                        experience = document.data["experience"] as Long,
                        status = RegistrationStatus.Teacher,
                    )
                }

                RegistrationStatus.Student -> {
                    val document = databases.getDocument(
                        databaseId = REPETITOR_DATABASE_ID,
                        collectionId = COLLECTION_STUDENTS,
                        documentId = uuid
                    )
                    return AppUser(
                        uuid = document.id,
                        name = document.data["name"] as String,
                        photo = document.data["photo"] as String,
                        status = RegistrationStatus.Student,
                    )
                }

                RegistrationStatus.Unknown -> return null
            }
        } catch (e: Exception) {
            logger(e)
            return null
        }
    }

    /**
     * Получаем свой профиль
     */
    suspend fun getMyProfile(status: RegistrationStatus): AppUser? =
        getUserProfile(
            uuid = account.get().id,
            status = status,
        )

    /**
     * Обновляем данные существующего пользователя
     */
    suspend fun updateUserInfo(user: AppUser): Boolean =
        try {
            when (user.status) {
                RegistrationStatus.Teacher -> {
                    databases.updateDocument(
                        databaseId = REPETITOR_DATABASE_ID,
                        collectionId = COLLECTION_TEACHERS,
                        documentId = user.uuid,
                        data = mapOf(
                            "name" to user.name,
                            "photo" to user.photo,
                            "direction" to user.direction,
                            "education" to user.education,
                            "experience" to user.experience
                        )
                    )
                    true
                }

                RegistrationStatus.Student -> {
                    databases.updateDocument(
                        databaseId = REPETITOR_DATABASE_ID,
                        collectionId = COLLECTION_STUDENTS,
                        documentId = user.uuid,
                        data = mapOf(
                            "name" to user.name,
                            "photo" to user.photo,
                        )
                    )
                    true
                }

                RegistrationStatus.Unknown -> false
            }
        } catch (e: Exception) {
            logger(e)
            false
        }

    /**
     * Метод для логгирования любых ошибок
     */
    private fun logger(e: Exception, comment: String? = null) =
        comment?.let {
            Timber.d("$comment: ${e.message}")
        } ?:
            Timber.d("Appwrite error: ${e.message}")


    companion object {

        const val HOST = "89.253.219.76"
        const val PROJECT = "649d4dbdcf623484dd45"
        const val ENDPOINT = "https://$HOST/v1"
        const val CALLBACK = "appwrite-callback-$PROJECT"
        const val REPETITOR_DATABASE_ID = "64a845269d40bb3fd619"
        const val FUNCTION_UPLOAD_IMAGE = "6643d97389086298495f"
        const val COLLECTION_TEACHERS = "64a8452b80905db4197c"
        const val COLLECTION_STUDENTS = "64a992739f88da356852"
        const val COLLECTION_DIRECTION = "64b11ca1e184ddbec98a"
        const val COLLECTION_EDUCATION = "64b11c868421f3ecce18"
    }
}