package com.beombeom.composeex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.beombeom.composeex.util.SetSystemUI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                color = Color.White,
                contentColor = Color.Black
            ) {
                SetSystemUI()
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Text("Hello, World!")
}


