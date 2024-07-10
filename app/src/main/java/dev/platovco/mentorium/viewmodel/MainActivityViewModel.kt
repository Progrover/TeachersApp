package dev.platovco.mentorium.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.platovco.mentorium.contract.MainUIEffect
import dev.platovco.mentorium.contract.MainUIEvent
import dev.platovco.mentorium.contract.MainUIState
import dev.platovco.mentorium.core.base.di.CoroutineQualifiers
import dev.platovco.mentorium.core.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,

) : BaseViewModel<MainUIEvent, MainUIState, MainUIEffect>(MainUIState()) {

    override fun handleUIEvent(event: MainUIEvent) {

    }
}