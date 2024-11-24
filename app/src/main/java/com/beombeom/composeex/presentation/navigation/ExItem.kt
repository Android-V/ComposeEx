package com.beombeom.composeex.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.beombeom.composeex.presentation.examples.BottomSheetMenu
import com.beombeom.composeex.presentation.examples.BottomSheetScaffoldEx
import com.beombeom.composeex.presentation.examples.ModalBottomSheetEx
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_BOTTOM_SHEET_MENU
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_BOTTOM_SHEET_SCAFFOLD
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_MODAL_BOTTOM_SHEET
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_BOTTOM_SHEET_MENU
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_BOTTOM_SHEET_SCAFFOLD
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_MODAL_BOTTOM_SHEET
import com.beombeom.composeex.presentation.navigation.Routes.BOTTOM_SHEET_MENU
import com.beombeom.composeex.presentation.navigation.Routes.BOTTOM_SHEET_SCAFFOLD
import com.beombeom.composeex.presentation.navigation.Routes.MODAL_BOTTOM_SHEET

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

        fun getExList(): List<ExItem> = listOf(
            BottomSheet,
            BottomSheetScaffold,
            ModalBottomSheet,
            BottomSheet,
            BottomSheet,
            BottomSheet,
        )
    }
}

object Routes {
    const val BOTTOM_SHEET_MENU = "bottomSheetMenu"

    // sub routes
    const val BOTTOM_SHEET_SCAFFOLD = "bottomSheetScaffold"
    const val MODAL_BOTTOM_SHEET = "modalBottomSheet"
}

object ExTitles {
    const val TITLE_BOTTOM_SHEET_MENU = "Bottom Sheet Ex"
    const val TITLE_BOTTOM_SHEET_SCAFFOLD = "Bottom Sheet Scaffold Ex"
    const val TITLE_MODAL_BOTTOM_SHEET = "Modal Bottom Sheet Ex"
}

object SubCategories {
    const val SUB_BOTTOM_SHEET = "Bottom Sheet"
}

object ExDescriptions {
    const val DESCRIPTION_BOTTOM_SHEET_MENU = "2가지 방법으로 구현해보는 Bottom Sheet"
    const val DESCRIPTION_BOTTOM_SHEET_SCAFFOLD = "BottomSheetScaffold로 구현해보는 Bottom Sheet"
    const val DESCRIPTION_MODAL_BOTTOM_SHEET = "ModalBottomSheet으로 구현해보는 Bottom Sheet"
}