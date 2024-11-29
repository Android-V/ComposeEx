package com.beombeom.composeex.presentation.examples.lazyLow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.beombeom.composeex.R
import com.beombeom.composeex.presentation.examples.bottomSheet.InfoText
import kotlinx.coroutines.launch

@Composable
fun RecyclerViewEx() {
    val viewModel = viewModel<RecyclerViewViewModel>()
    val items by remember { viewModel.items }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val visibleItemCount by remember { derivedStateOf { listState.layoutInfo.visibleItemsInfo.size } }
    val totalItemCount by remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                InfoText(text = "Total items: $totalItemCount")
                InfoText(text = "First visible item index: $firstVisibleItemIndex")
                InfoText(text = "Visible items count: $visibleItemCount")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val firstVisibleItem = listState.firstVisibleItemIndex
                            if (firstVisibleItem > 0) {
                                listState.animateScrollToItem(firstVisibleItem - 1)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White,
                    )
                ) {
                    Text("Previous Item")
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val firstVisibleItem = listState.firstVisibleItemIndex
                            listState.animateScrollToItem(firstVisibleItem + 1)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White,
                    )
                ) {
                    Text("Next Item")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White,
                    )
                ) {
                    Text("Go to First")
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(listState.layoutInfo.totalItemsCount - 1)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White,
                    )
                ) {
                    Text("Go to Last")
                }
            }

            VerticalRecyclerView(
                items = items,
                listState = listState
            )
        }
    }
}

@Composable
fun VerticalRecyclerView(items: List<RecyclerViewEntity>, listState: LazyListState) {
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
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
            containerColor = colorResource(id = R.color.aquamarine)
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
