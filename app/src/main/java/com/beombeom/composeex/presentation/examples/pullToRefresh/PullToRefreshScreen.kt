package com.beombeom.composeex.presentation.examples.pullToRefresh

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun FullToRefreshEx() {
    val viewModel: PullToRefreshViewModel = viewModel()
    val state by viewModel.screenState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        HomeScreenContent(
            state = state,
            onRefreshTrigger = viewModel::onPullToRefreshTrigger,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeScreenContent(
    state: ScreenState,
    onRefreshTrigger: () -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshWrapper(
        isRefreshing = state.isRefreshing,
        onRefresh = onRefreshTrigger,
        contentAlignment = Alignment.TopStart,
        modifier = modifier
    ) {
        RandomNumList(state.items)
    }
}

/**
 * This essentially copies PullToRefreshBox since we can't use it directly
 * as it doesn't expose enabled parameter, which we need to disable pull-to-refresh
 * in offline mode
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PullToRefreshWrapper(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit,
) {
    val refreshState = rememberPullToRefreshState()

    Box(
        modifier.pullToRefresh(
            state = refreshState,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            enabled = enabled,
        ),
        contentAlignment = contentAlignment,
    ) {
        content()
        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            state = refreshState,
        )
    }
}


@Composable
private fun RandomNumList(items: List<RandomNum>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { item ->
            RandomNumItem(item = item)
        }
    }
}

@Composable
private fun RandomNumItem(item: RandomNum) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = item.id.toString(),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    }
}


