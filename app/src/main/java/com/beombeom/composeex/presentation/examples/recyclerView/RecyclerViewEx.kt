package com.beombeom.composeex.presentation.examples.recyclerView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.beombeom.composeex.presentation.examples.bottomSheet.InfoText

@Composable
fun RecyclerViewEx() {
    val viewModel = viewModel<HorizontalRecyclerViewViewModel>()
    val items by remember { viewModel.items }
    val listState = rememberLazyListState()
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val visibleItemCount by remember { derivedStateOf { listState.layoutInfo.visibleItemsInfo.size } }
    val totalItemCount by remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        InfoText(text = "Total items: $totalItemCount")
        InfoText(text = "First visible item index: $firstVisibleItemIndex")
        InfoText(text = "Visible items count: $visibleItemCount")
        Spacer(modifier = Modifier.height(10.dp))

        CustomVerticalRecyclerView(
            items = items,
            listState = listState
        )
    }
}

@Composable
fun CustomVerticalRecyclerView(items: List<RecyclerViewEntity>, listState: LazyListState) {
    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            itemsIndexed(items) { _, item ->
                CustomUserProfile(
                    id = item.id
                )
            }
        }
    )
}

@Composable
fun CustomUserProfile(id: Int) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(130.dp)
            .width(210.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFECEFF1),
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "# $id",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}


