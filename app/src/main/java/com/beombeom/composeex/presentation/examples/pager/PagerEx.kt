package com.beombeom.composeex.presentation.examples.pager

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beombeom.composeex.presentation.examples.bottomSheet.InfoText

@Composable
fun PagerEx() {
    Box(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState { 10 }
        var scrollLastPage by remember { mutableStateOf(false) }
        var scrollFirstPage by remember { mutableStateOf(false) }

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
                pagerState.animateScrollToPage(0)
                scrollLastPage = false
            }

            if (scrollFirstPage) {
                pagerState.animateScrollToPage(pagerState.pageCount - 1)
                scrollFirstPage = false
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            InfoText(
                text = "isScrollInProgress : ${pagerState.isScrollInProgress}"
            )
            InfoText(
                text = "pageCount : ${pagerState.pageCount}"
            )
            InfoText(
                text = "currentPage : ${pagerState.currentPage}"
            )
            InfoText(
                text = "currentPageOffsetFraction : ${pagerState.currentPageOffsetFraction}"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                CustomViewPager(pagerState = pagerState)
            }

            Spacer(modifier = Modifier.height(10.dp))

            PageIndicator(
                itemSize = pagerState.pageCount,
                pagerState = pagerState
            )
        }
    }
}


@Composable
fun CustomViewPager(pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) { page ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 20.dp),
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
}

@Composable
fun PageIndicator(
    itemSize: Int,
    pagerState: PagerState
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
            )
        }
    }
}
