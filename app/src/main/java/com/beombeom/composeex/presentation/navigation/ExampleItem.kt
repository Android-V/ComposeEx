package com.beombeom.composeex.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.beombeom.composeex.presentation.examples.BottomSheetEx
import com.beombeom.composeex.presentation.navigation.ExDescriptions.DESCRIPTION_BOTTOM_SHEET
import com.beombeom.composeex.presentation.navigation.ExTitles.TITLE_BOTTOM_SHEET
import com.beombeom.composeex.presentation.navigation.Routes.BOTTOM_SHEET

data class ExampleItem(
    val route: String,
    val title: String,
    val content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
    val description: String = "",
) {
    companion object {
        private val BottomSheet = ExampleItem(
            route = BOTTOM_SHEET,
            title = TITLE_BOTTOM_SHEET,
            content = { BottomSheetEx() },
            description = DESCRIPTION_BOTTOM_SHEET
        )

        fun getExampleList(): List<ExampleItem> = listOf(
            BottomSheet,BottomSheet,BottomSheet,BottomSheet,BottomSheet,BottomSheet,
        )
    }
}

object Routes {
    const val BOTTOM_SHEET = "bottomSheet"
}

object ExTitles {
    const val TITLE_BOTTOM_SHEET = "Bottom Sheet Ex"
}

object ExDescriptions {
    const val DESCRIPTION_BOTTOM_SHEET = "3가지 방법으로 구현해보는 Bottom Sheet"
}