package com.beombeom.composeex.presentation.examples.pager

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.beombeom.composeex.presentation.MainHeader
import com.beombeom.composeex.presentation.examples.bottomSheet.InfoText
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun PagerEx(title : String) {
    val viewModel = viewModel<PagerViewModel>()
    val pagerState by viewModel.pagerState.collectAsState()
    val isAutoScrollEnabled by viewModel.isAutoScrollEnabled.collectAsState()
    val movePageScope = rememberCoroutineScope()
    val autoScrollScope = rememberCoroutineScope()

    LaunchedEffect(isAutoScrollEnabled) {
        if (isAutoScrollEnabled) {
            autoScrollScope.launch {
                while (isAutoScrollEnabled) {
                    delay(1500)
                    try {
                        if (!pagerState.isScrollInProgress) {
                            val nextPage = viewModel.getNextPage()
                            goPage(pagerState, nextPage)
                        }
                    } catch (e: Exception) {
                        Log.e("PagerEx", "Exception in autoScroll: ${e.message}", e)
                    }
                }
            }
        } else {
            autoScrollScope.coroutineContext.cancelChildren()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        MainHeader(title = title)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            InfoText(text = "isScrollInProgress : ${pagerState.isScrollInProgress}")
            InfoText(text = "pageCount : ${pagerState.pageCount}")
            InfoText(text = "currentPage : ${pagerState.currentPage}")
            InfoText(text = "currentPageOffsetFraction : ${pagerState.currentPageOffsetFraction}")

            Button(
                onClick = { viewModel.toggleAutoScroll() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White,
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = if (isAutoScrollEnabled) "Auto Scroll Off" else "Auto Scroll On"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                CustomViewPager(pagerState = pagerState)

                IconButton(
                    onClick = {
                        movePageScope.launch {
                            val prevPage = viewModel.getPreviousPage()
                            goPage(pagerState, prevPage)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous Page"
                    )
                }

                IconButton(
                    onClick = {
                        movePageScope.launch {
                            val nextPage = viewModel.getNextPage()
                            goPage(pagerState, nextPage)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next Page"
                    )
                }
            }

            PageIndicator(
                itemSize = pagerState.pageCount,
                pagerState = pagerState,
                onDotClick = { page ->
                    movePageScope.launch {
                        if (!pagerState.isScrollInProgress) {
                            goPage(pagerState, page)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CustomViewPager(pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) { page ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 20.dp)
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                    alpha = lerp(
                        start = 0.2f,
                        stop = 1f,
                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                    )
                },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            elevation = CardDefaults.cardElevation(5.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "This is the ${page}th page.",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    ),
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun PageIndicator(
    itemSize: Int,
    pagerState: PagerState,
    onDotClick: (Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(itemSize) { index ->
            val color = if (pagerState.currentPage == index) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(10.dp)
                    .clickable { onDotClick(index) }
            )
        }
    }
}

private suspend fun goPage(pagerState: PagerState, nextPage: Int) {
    if (!pagerState.isScrollInProgress) {
        pagerState.animateScrollToPage(nextPage)
    } else {
        Log.d("PagerEx", "Skipping goPage: User is scrolling.")
    }
}

@Composable
fun PagerManualEx() {
    Box(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState { 10 }
        var scrollLastPage by remember { mutableStateOf(false) }
        var scrollFirstPage by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(key1 = pagerState.isScrollInProgress) {
            if (pagerState.currentPage == pagerState.pageCount - 1
                && pagerState.isScrollInProgress
                && pagerState.currentPageOffsetFraction == 0f
            ) {
                scrollLastPage = true
            } else if (pagerState.currentPage == 0
                && pagerState.isScrollInProgress
                && pagerState.currentPageOffsetFraction == 0f
            ) {
                scrollFirstPage = true
            }

            if (scrollLastPage) {
                goPage(pagerState, 0)
                scrollLastPage = false
            }

            if (scrollFirstPage) {
                goPage(pagerState, pagerState.pageCount - 1)
                scrollFirstPage = false
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            InfoText(text = "isScrollInProgress : ${pagerState.isScrollInProgress}")
            InfoText(text = "pageCount : ${pagerState.pageCount}")
            InfoText(text = "currentPage : ${pagerState.currentPage}")
            InfoText(text = "currentPageOffsetFraction : ${pagerState.currentPageOffsetFraction}")

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                CustomViewPager(pagerState = pagerState)
            }

            Spacer(modifier = Modifier.height(10.dp))

            PageIndicator(
                itemSize = pagerState.pageCount,
                pagerState = pagerState,
                onDotClick = { page ->
                    coroutineScope.launch {
                        if (!pagerState.isScrollInProgress) {
                            goPage(pagerState, page)
                        }
                    }
                }
            )
        }
    }
}