package dev.platovco.mentorium.core.base.domain.model

data class AppUser(
    val uuid: String,
    val name: String,
    val photo: String?,
    val direction: String? = null,
    val education: String? = null,
    val experience: Long? = null,
    val status: RegistrationStatus,
)