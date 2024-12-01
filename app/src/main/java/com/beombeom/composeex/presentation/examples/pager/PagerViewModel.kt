package com.beombeom.composeex.presentation.examples.pager

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PagerViewModel : ViewModel() {
    private val _pagerState = MutableStateFlow(PagerState { 10 })
    val pagerState: StateFlow<PagerState> = _pagerState.asStateFlow()

    private val _isAutoScrollEnabled = MutableStateFlow(false)
    val isAutoScrollEnabled: StateFlow<Boolean> = _isAutoScrollEnabled.asStateFlow()

    fun toggleAutoScroll() {
        _isAutoScrollEnabled.value = !_isAutoScrollEnabled.value
    }

    fun getNextPage(): Int {
        val currentPagerState = _pagerState.value
        return (currentPagerState.currentPage + 1) % currentPagerState.pageCount
    }

    fun getPreviousPage(): Int {
        val currentPagerState = _pagerState.value
        return if (currentPagerState.currentPage == 0) {
            currentPagerState.pageCount - 1
        } else {
            currentPagerState.currentPage - 1
        }
    }
}
