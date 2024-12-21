package com.beombeom.composeex.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.beombeom.composeex.presentation.examples.bottomSheet.BottomSheetMenu
import com.beombeom.composeex.presentation.examples.bottomSheet.BottomSheetScaffoldEx
import com.beombeom.composeex.presentation.examples.bottomSheet.ModalBottomSheetEx
import com.beombeom.composeex.presentation.examples.pager.PagerEx
import com.beombeom.composeex.presentation.examples.lazyLow.RecyclerViewEx
import com.beombeom.composeex.presentation.examples.pullToRefresh.FullToRefreshEx
import com.beombeom.composeex.presentation.examples.sideEffect.SideEffectEx
import com.beombeom.composeex.presentation.examples.videoPlayer.VideoPlayerEx
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_BOTTOM_SHEET_MENU
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_BOTTOM_SHEET_SCAFFOLD
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_MODAL_BOTTOM_SHEET
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_PAGER
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_PULL_TO_REFRESH
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_RECYCLERVIEW
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_SIDE_EFFECT
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_VIDEO_PLAYER
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_BOTTOM_SHEET_MENU
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_BOTTOM_SHEET_SCAFFOLD
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_MODAL_BOTTOM_SHEET
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_PAGER
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_PULL_TO_REFRESH
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_RECYCLERVIEW
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_SIDE_EFFECT
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_VIDEO_PLAYER
import com.beombeom.composeex.presentation.navigation.Routes.BOTTOM_SHEET_MENU
import com.beombeom.composeex.presentation.navigation.Routes.BOTTOM_SHEET_SCAFFOLD
import com.beombeom.composeex.presentation.navigation.Routes.MODAL_BOTTOM_SHEET
import com.beombeom.composeex.presentation.navigation.Routes.PAGER
import com.beombeom.composeex.presentation.navigation.Routes.PULL_TO_REFRESH
import com.beombeom.composeex.presentation.navigation.Routes.RECYCLERVIEW
import com.beombeom.composeex.presentation.navigation.Routes.SIDE_EFFECT
import com.beombeom.composeex.presentation.navigation.Routes.VIDEO_PLAYER

data class ExItem(
    val route: String,
    val title: String,
    val subCategory: String? = "",
    val content: @Composable (NavController?, NavBackStackEntry) -> Unit,
    val description: String = "",
) {
    companion object {
        private val BottomSheet = ExItem(
            route = BOTTOM_SHEET_MENU,
            title = TITLE_BOTTOM_SHEET_MENU,
            content = { navController, _ ->
                if (navController != null) {
                    BottomSheetMenu(navController)
                }
            },
            description = DESCRIPTION_BOTTOM_SHEET_MENU
        )

        private val BottomSheetScaffold = ExItem(
            route = BOTTOM_SHEET_SCAFFOLD,
            title = TITLE_BOTTOM_SHEET_SCAFFOLD,
            subCategory = SubCategories.SUB_BOTTOM_SHEET,
            content = { _, _ -> BottomSheetScaffoldEx() },
            description = DESCRIPTION_BOTTOM_SHEET_SCAFFOLD
        )

        private val ModalBottomSheet = ExItem(
            route = MODAL_BOTTOM_SHEET,
            title = TITLE_MODAL_BOTTOM_SHEET,
            subCategory = SubCategories.SUB_BOTTOM_SHEET,
            content = { _, _ -> ModalBottomSheetEx() },
            description = DESCRIPTION_MODAL_BOTTOM_SHEET
        )

        private val Pager = ExItem(
            route = PAGER,
            title = TITLE_PAGER,
            content = { _, _ -> PagerEx() },
            description = DESCRIPTION_PAGER
        )

        private val RecyclerView = ExItem(
            route = RECYCLERVIEW,
            title = TITLE_RECYCLERVIEW,
            content = { _, _ -> RecyclerViewEx() },
            description = DESCRIPTION_RECYCLERVIEW
        )

        private val VideoPlayer = ExItem(
            route = VIDEO_PLAYER,
            title = TITLE_VIDEO_PLAYER,
            content = { _, _ -> VideoPlayerEx() },
            description = DESCRIPTION_VIDEO_PLAYER
        )

        private val SideEffect = ExItem(
            route = SIDE_EFFECT,
            title = TITLE_SIDE_EFFECT,
            content = { _, _ -> SideEffectEx() },
            description = DESCRIPTION_SIDE_EFFECT
        )

        private val PullToRefresh = ExItem(
            route = PULL_TO_REFRESH,
            title = TITLE_PULL_TO_REFRESH,
            content = { _, _ -> FullToRefreshEx() },
            description = DESCRIPTION_PULL_TO_REFRESH
        )

        fun getExList(): List<ExItem> = listOf(
            BottomSheet,
            BottomSheetScaffold,
            ModalBottomSheet,
            Pager,
            RecyclerView,
            VideoPlayer,
            SideEffect,
            PullToRefresh,
        )
    }
}

object Routes {
    const val BOTTOM_SHEET_MENU = "bottomSheetMenu"
    const val PAGER = "pager"
    const val RECYCLERVIEW = "recyclerView"
    const val VIDEO_PLAYER = "videoPlayer"
    const val SIDE_EFFECT = "sideEffect"
    const val PULL_TO_REFRESH = "pullToRefresh"

    // sub routes
    const val BOTTOM_SHEET_SCAFFOLD = "bottomSheetScaffold"
    const val MODAL_BOTTOM_SHEET = "modalBottomSheet"
}

object ExTitles {
    const val TITLE_BOTTOM_SHEET_MENU = "Bottom Sheet Ex"
    const val TITLE_BOTTOM_SHEET_SCAFFOLD = "Bottom Sheet Scaffold Ex"
    const val TITLE_MODAL_BOTTOM_SHEET = "Modal Bottom Sheet Ex"
    const val TITLE_PAGER = "Pager Ex"
    const val TITLE_RECYCLERVIEW = "RecyclerView Ex"
    const val TITLE_VIDEO_PLAYER = "VideoPlayer Ex"
    const val TITLE_SIDE_EFFECT = "SideEffect Ex"
    const val TITLE_PULL_TO_REFRESH = "Pull To Refresh Ex"
}

object SubCategories {
    const val SUB_BOTTOM_SHEET = "Bottom Sheet"
}

object ExDescriptions {
    const val DESCRIPTION_BOTTOM_SHEET_MENU = "2가지 방법으로 구현해보는 Bottom Sheet"
    const val DESCRIPTION_BOTTOM_SHEET_SCAFFOLD = "BottomSheetScaffold로 구현해보는 Bottom Sheet"
    const val DESCRIPTION_MODAL_BOTTOM_SHEET = "ModalBottomSheet으로 구현해보는 Bottom Sheet"
    const val DESCRIPTION_PAGER = "HorizontalPager로 구현해보는 Screen의 Pager"
    const val DESCRIPTION_RECYCLERVIEW = "LazyRow로 구현해보는 RecyclerView"
    const val DESCRIPTION_VIDEO_PLAYER = "ExoPlayer를 사용하여 구현해보는 VideoPlayer"
    const val DESCRIPTION_SIDE_EFFECT = "다양한 SideEffect를 사용해보는 예제"
    const val DESCRIPTION_PULL_TO_REFRESH = "PullToRefresh를 구현해보는 예제"
}