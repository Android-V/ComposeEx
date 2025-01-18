package com.beombeom.composeex.presentation.examples.pullToRefresh

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.beombeom.composeex.presentation.MainHeader

@Composable
internal fun FullToRefreshEx(title : String) {
    val viewModel: PullToRefreshViewModel = viewModel()
    val state by viewModel.screenState.collectAsState()
    Column {
        MainHeader(title = title)

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            FullToRefreshExContent(
                state = state,
                onRefreshTrigger = viewModel::onPullToRefreshTrigger,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FullToRefreshExContent(
    state: ScreenState,
    onRefreshTrigger: () -> Unit,
    modifier: Modifier = Modifier
) {
    val refreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier.pullToRefresh(
            state = refreshState,
            isRefreshing = state.isRefreshing,
            onRefresh = onRefreshTrigger,
            enabled = true,
        ),
        contentAlignment = Alignment.TopStart,
    ) {
        RandomNumList(state.items)
        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = state.isRefreshing,
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
                text = "Item Number is " + item.id.toString(),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    }
}
