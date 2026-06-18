package com.beombeom.composeex.presentation.examples.bottomSheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.beombeom.composeex.presentation.common.CardSection
import com.beombeom.composeex.presentation.common.MainHeader
import com.beombeom.composeex.presentation.navigation.ExItem
import com.beombeom.composeex.presentation.navigation.SubCategories

@Composable
fun BottomSheetMenu(backStack: NavBackStack<NavKey>) {
    val filteredItems =
        ExItem.getExList().filter { it.subCategory == SubCategories.SUB_BOTTOM_SHEET }

    Box(modifier = Modifier.fillMaxSize()) {
        MenuContent(filteredItems = filteredItems, backStack = backStack)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuContent(
    filteredItems: List<ExItem>,
    backStack: NavBackStack<NavKey>,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        stickyHeader {
            MainHeader(title = SubCategories.SUB_BOTTOM_SHEET)
        }

        items(filteredItems) { example ->
            Column(modifier = Modifier.fillMaxWidth()) {
                CardSection(
                    exampleTitle = example.title,
                    exampleDescription = example.description,
                    onButtonClick = {
                        backStack.add(example.route)
                    },
                )
            }
        }
    }
}
