package com.beombeom.composeex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.beombeom.composeex.R
import com.beombeom.composeex.presentation.common.CardSection
import com.beombeom.composeex.presentation.common.MainHeader
import com.beombeom.composeex.presentation.examples.bottomSheet.BottomSheetMenu
import com.beombeom.composeex.presentation.examples.bottomSheet.BottomSheetScaffoldEx
import com.beombeom.composeex.presentation.examples.bottomSheet.ModalBottomSheetEx
import com.beombeom.composeex.presentation.examples.dropdown.DropdownMenuEx
import com.beombeom.composeex.presentation.examples.lazyLow.RecyclerViewEx
import com.beombeom.composeex.presentation.examples.pager.PagerEx
import com.beombeom.composeex.presentation.examples.pullToRefresh.FullToRefreshEx
import com.beombeom.composeex.presentation.examples.sideEffect.SideEffectEx
import com.beombeom.composeex.presentation.examples.videoPlayer.VideoPlayerEx
import com.beombeom.composeex.presentation.navigation.ExItem
import com.beombeom.composeex.presentation.navigation.ExNavRoute
import com.beombeom.composeex.presentation.navigation.ExTitles
import com.beombeom.composeex.presentation.util.SetSystemUI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MainViewModel>()
            Surface(
                color = Color.White,
                contentColor = Color.Black
            ) {
                val backStack = rememberNavBackStack(ExNavRoute.Main)
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryDecorators = listOf(
                        // rememberSaveable 동작 보장 (configuration change, process death 대응)
                        rememberSaveableStateHolderNavEntryDecorator(),
                        // ViewModel을 NavEntry 생명주기에 스코프
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        entry<ExNavRoute.Main> {
                            SetSystemUI()
                            MainScreen(viewModel, backStack)
                        }
                        entry<ExNavRoute.BottomSheetMenu> {
                            BottomSheetMenu(backStack)
                        }
                        entry<ExNavRoute.BottomSheetScaffold> {
                            BottomSheetScaffoldEx(ExTitles.TITLE_BOTTOM_SHEET_SCAFFOLD)
                        }
                        entry<ExNavRoute.ModalBottomSheet> {
                            ModalBottomSheetEx(ExTitles.TITLE_MODAL_BOTTOM_SHEET)
                        }
                        entry<ExNavRoute.Pager> {
                            PagerEx(ExTitles.TITLE_PAGER)
                        }
                        entry<ExNavRoute.RecyclerView> {
                            RecyclerViewEx(ExTitles.TITLE_RECYCLERVIEW)
                        }
                        entry<ExNavRoute.VideoPlayer> {
                            VideoPlayerEx(ExTitles.TITLE_VIDEO_PLAYER)
                        }
                        entry<ExNavRoute.SideEffect> {
                            SideEffectEx(ExTitles.TITLE_SIDE_EFFECT)
                        }
                        entry<ExNavRoute.PullToRefresh> {
                            FullToRefreshEx(ExTitles.TITLE_PULL_TO_REFRESH)
                        }
                        entry<ExNavRoute.DropdownMenu> {
                            DropdownMenuEx(ExTitles.TITLE_DROPDOWNMENU)
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel, backStack: NavBackStack<NavKey>) {
    val context = LocalContext.current
    val filteredItems = ExItem.getExList().filter { it.subCategory.isNullOrEmpty() }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            stickyHeader {
                MainHeader(title = stringResource(R.string.title_main_header))
            }

            items(filteredItems) { example ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    CardSection(
                        context = context,
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
}

fun getTextStyle(fontSize: Int) = TextStyle(
    fontSize = fontSize.sp,
    letterSpacing = (-0.01).em,
    lineHeight = 1.4.em
)