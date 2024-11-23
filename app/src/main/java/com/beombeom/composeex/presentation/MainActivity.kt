package com.beombeom.composeex.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Typography
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.beombeom.composeex.R
import com.beombeom.composeex.presentation.navigation.ExampleItem
import com.beombeom.composeex.presentation.util.SetSystemUI
import com.beombeom.composeex.presentation.util.noRippleClickable

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
                    ExampleItem.getExampleList().forEach { item ->
                        composable(
                            route = item.route,
                            content = item.content
                        )
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
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            stickyHeader {
                MainHeader(title = stringResource(R.string.title_main_header))
            }


            items(ExampleItem.getExampleList()) { example ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    CardSection(
                        context = context,
                        exampleTitle = example.title,
                        exampleDescription = example.description,
                        onButtonClick = {
                            navController.navigate(example.route)
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun MainHeader(title: String) {
    val context = LocalContext.current
    val dynamicColorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        dynamicLightColorScheme(context)
    } else {
        lightColorScheme()
    }

    MaterialTheme(
        colorScheme = dynamicColorScheme,
        typography = Typography()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.displayMedium,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun CardSection(
    context: Context,
    exampleTitle: String,
    exampleDescription: String,
    onButtonClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            androidx.compose.material.Text(
                modifier = Modifier.fillMaxWidth(),
                text = exampleTitle,
                color = Color.White,
                style = getTextStyle(18)
            )

            Spacer(modifier = Modifier.height(4.dp))

            androidx.compose.material.Text(
                modifier = Modifier.fillMaxWidth(),
                text = exampleDescription,
                style = getTextStyle(14),
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .noRippleClickable {
                            onButtonClick.invoke()
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        text = "Go Example",
                        color = Color.Black,
                        style = getTextStyle(16)
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