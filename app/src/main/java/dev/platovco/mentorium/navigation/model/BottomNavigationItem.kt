package dev.platovco.mentorium.navigation.model

import dev.platovco.mentorium.R
import dev.platovco.mentorium.feature.chat.api.ChatFeature
import dev.platovco.mentorium.feature.joblist.api.JobListFeature
import dev.platovco.mentorium.feature.mainscreen.api.MainScreenFeature
import dev.platovco.mentorium.feature.profile.api.ProfileFeature

sealed class BottomNavigationItem(
    val route: String,
    val iconResId: Int,
) {

    data object MainScreen : BottomNavigationItem(
        route = MainScreenFeature.ROUTE_NAME,
        iconResId = R.drawable.ic_meet,
    )

    data object JobScreen : BottomNavigationItem(
        route = JobListFeature.ROUTE_NAME,
        iconResId = R.drawable.ic_jobs,
    )

    data object ChatScreen : BottomNavigationItem(
        route = ChatFeature.ROUTE_NAME,
        iconResId = R.drawable.ic_chat,
    )

    data object ProfileScreen : BottomNavigationItem(
        route = ProfileFeature.ROUTE_NAME,
        iconResId = R.drawable.ic_profile,
    )
}