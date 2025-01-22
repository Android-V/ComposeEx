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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.beombeom.composeex.R
import com.beombeom.composeex.presentation.common.CardSection
import com.beombeom.composeex.presentation.common.MainHeader
import com.beombeom.composeex.presentation.navigation.ExItem
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
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        SetSystemUI()
                        MainScreen(viewModel, navController)
                    }
                    // Register routes from ExItem
                    ExItem.getExList().forEach { item ->
                        composable(
                            route = item.route
                        ) {
                            // Pass navController if subCategory exists
                            if (item.subCategory.isNullOrEmpty()) {
                                item.content(navController, it)
                            } else {
                                item.content(null, it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
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
                            navController.navigate("${example.route}?title=${example.title}")
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