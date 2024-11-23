package com.beombeom.composeex.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetSystemUI() {
    val systemUiController = rememberSystemUiController()

    // 상단 상태바(Status Bar)와 하단 네비게이션 바(Navigation Bar)를 동일한 색상으로 지정합니다.
//    systemUiController.setSystemBarsColor(Color.Blue)

    // 상단 상태바(Status Bar) 색상을 지정합니다.
    systemUiController.setStatusBarColor(Color.Yellow)
    // 하단 네비게이션 바(Navigation Bar) 색상을 지정합니다.
    systemUiController.setNavigationBarColor(Color.Red)
}