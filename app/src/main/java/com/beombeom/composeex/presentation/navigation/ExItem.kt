package com.beombeom.composeex.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class ExNavRoute : NavKey {

    @Serializable
    data object Main : ExNavRoute()

    @Serializable
    data object BottomSheetMenu : ExNavRoute()

    @Serializable
    data object BottomSheetScaffold : ExNavRoute()

    @Serializable
    data object ModalBottomSheet : ExNavRoute()

    @Serializable
    data object Pager : ExNavRoute()

    @Serializable
    data object RecyclerView : ExNavRoute()

    @Serializable
    data object VideoPlayer : ExNavRoute()

    @Serializable
    data object SideEffect : ExNavRoute()

    @Serializable
    data object PullToRefresh : ExNavRoute()

    @Serializable
    data object DropdownMenu : ExNavRoute()
}

data class ExItem(
    val route: ExNavRoute,
    val title: String,
    val subCategory: String? = "",
    val description: String = "",
) {
    companion object {
        private val BottomSheet = ExItem(
            route = ExNavRoute.BottomSheetMenu,
            title = ExTitles.TITLE_BOTTOM_SHEET_MENU,
            description = ExDescriptions.DESCRIPTION_BOTTOM_SHEET_MENU,
        )

        private val BottomSheetScaffold = ExItem(
            route = ExNavRoute.BottomSheetScaffold,
            title = ExTitles.TITLE_BOTTOM_SHEET_SCAFFOLD,
            subCategory = SubCategories.SUB_BOTTOM_SHEET,
            description = ExDescriptions.DESCRIPTION_BOTTOM_SHEET_SCAFFOLD,
        )

        private val ModalBottomSheet = ExItem(
            route = ExNavRoute.ModalBottomSheet,
            title = ExTitles.TITLE_MODAL_BOTTOM_SHEET,
            subCategory = SubCategories.SUB_BOTTOM_SHEET,
            description = ExDescriptions.DESCRIPTION_MODAL_BOTTOM_SHEET,
        )

        private val Pager = ExItem(
            route = ExNavRoute.Pager,
            title = ExTitles.TITLE_PAGER,
            description = ExDescriptions.DESCRIPTION_PAGER,
        )

        private val RecyclerView = ExItem(
            route = ExNavRoute.RecyclerView,
            title = ExTitles.TITLE_RECYCLERVIEW,
            description = ExDescriptions.DESCRIPTION_RECYCLERVIEW,
        )

        private val VideoPlayer = ExItem(
            route = ExNavRoute.VideoPlayer,
            title = ExTitles.TITLE_VIDEO_PLAYER,
            description = ExDescriptions.DESCRIPTION_VIDEO_PLAYER,
        )

        private val SideEffect = ExItem(
            route = ExNavRoute.SideEffect,
            title = ExTitles.TITLE_SIDE_EFFECT,
            description = ExDescriptions.DESCRIPTION_SIDE_EFFECT,
        )

        private val PullToRefresh = ExItem(
            route = ExNavRoute.PullToRefresh,
            title = ExTitles.TITLE_PULL_TO_REFRESH,
            description = ExDescriptions.DESCRIPTION_PULL_TO_REFRESH,
        )

        private val DropdownMenu = ExItem(
            route = ExNavRoute.DropdownMenu,
            title = ExTitles.TITLE_DROPDOWNMENU,
            description = ExDescriptions.DESCRIPTION_DROPDOWNMENU,
        )

        fun getExList(): List<ExItem> =
            listOf(
                BottomSheet,
                BottomSheetScaffold,
                ModalBottomSheet,
                Pager,
                RecyclerView,
                VideoPlayer,
                SideEffect,
                PullToRefresh,
                DropdownMenu,
            )
    }
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
    const val TITLE_DROPDOWNMENU = "Dropdown Menu Ex"
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
    const val DESCRIPTION_DROPDOWNMENU = "드롭다운 메뉴를 구현해보는 예제"
}
