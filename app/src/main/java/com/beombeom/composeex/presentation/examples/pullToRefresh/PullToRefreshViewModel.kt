package com.beombeom.composeex.presentation.examples.pullToRefresh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PullToRefreshViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    private val _counter = MutableStateFlow(0)

    val screenState: StateFlow<ScreenState> = combine(_counter, _isRefreshing) { counter, isRefreshing ->
        ScreenState(
            items = List(counter) { index -> RandomNum(index) },
            isRefreshing = isRefreshing
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScreenState()
    )

    fun onPullToRefreshTrigger() {
        _isRefreshing.update { true }
        viewModelScope.launch {
            delay(1000)
            _counter.update { it + 1 }
            _isRefreshing.update { false }
        }
    }
}

