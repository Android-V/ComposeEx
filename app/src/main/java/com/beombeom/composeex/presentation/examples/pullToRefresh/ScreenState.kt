package com.beombeom.composeex.presentation.examples.pullToRefresh

data class ScreenState(
    val items: List<RandomNum> = listOf(),
    val isRefreshing: Boolean = false
)
