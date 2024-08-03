package dev.platovco.mentorium.core.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.platovco.mentorium.core.base.presentation.mvi.UIEffect
import dev.platovco.mentorium.core.base.presentation.mvi.UIEvent
import dev.platovco.mentorium.core.base.presentation.mvi.UIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UIEvent, State : UIState, Effect : UIEffect>(
    initialState: State,
) : ViewModel() {

    protected var shouldReloadScreenDataOnReopen: Boolean = false

    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    constructor(initialStateInitializer: () -> State) : this(initialStateInitializer.invoke())

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect(::handleUIEvent)
        }
    }

    fun setEvent(newEvent: Event) {
        viewModelScope.launch {
            event.emit(newEvent)
        }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setState(newState: State) =
        setState { newState }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch {
            _effect.send(effectValue)
        }
    }

    protected fun setEffect(effect: Effect) =
        setEffect { effect }

    protected abstract fun handleUIEvent(event: Event)



    open fun onScreenOpened() =
        Unit

    /**
     * метод перезагружает/обновляет данные, когда возвращаемся на текущий экран из другого места
     */
    protected fun setEffectWithDataInvalidationOnReturn(uiEffect: Effect) {
        setEffect(uiEffect)
        shouldReloadScreenDataOnReopen = true
    }

    protected open fun reloadScreenDataIfNeeded(reloadDataCallback: () -> Unit) {
        if (shouldReloadScreenDataOnReopen) {
            shouldReloadScreenDataOnReopen = false
            reloadDataCallback()
        }
    }
}