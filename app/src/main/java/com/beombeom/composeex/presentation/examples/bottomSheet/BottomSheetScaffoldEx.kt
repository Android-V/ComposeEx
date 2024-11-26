package com.beombeom.composeex.presentation.examples.bottomSheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScaffoldEx() {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
        snackbarHostState = remember { SnackbarHostState() }
    )

    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 90.dp,
        sheetTonalElevation  = 5.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),

            ) {
                InfoText("This is the BottomSheet")

                InfoText("isVisible: ${scaffoldState.bottomSheetState.isVisible}")

                InfoText("currentValue: ${scaffoldState.bottomSheetState.currentValue}")

                InfoText("targetValue: ${scaffoldState.bottomSheetState.targetValue}")

                InfoText(
                    text = try {
                        "offset: ${scaffoldState.bottomSheetState.requireOffset()}"
                    } catch (e: IllegalStateException) {
                        "offset: (Not Available)"
                    }
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch { scaffoldState.bottomSheetState.expand() }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White,
                        )
                    ) {
                        Text("Expand Sheet")
                    }
                    Button(
                        onClick = {
                            coroutineScope.launch { scaffoldState.bottomSheetState.partialExpand() }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White,
                        )
                    ) {
                        Text("Partial Expand Sheet")
                    }
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Message from BottomSheet!",
                                    actionLabel = "Dismiss"
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White,
                        )
                    ) {
                        Text("Show Snackbar")
                    }
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                InfoText(
                    text = "Snackbar Visible: ${scaffoldState.snackbarHostState.currentSnackbarData != null}"
                )
                InfoText(
                    text = "Snackbar Message: ${
                        scaffoldState.snackbarHostState.currentSnackbarData?.visuals?.message ?: "None"
                    }"
                )
                InfoText(
                    text = "Snackbar Action: ${
                        scaffoldState.snackbarHostState.currentSnackbarData?.visuals?.actionLabel ?: "None"
                    }"
                )

                Spacer(modifier = Modifier.height(8.dp))

                InfoText("isVisible: ${scaffoldState.bottomSheetState.isVisible}")

                InfoText("currentValue: ${scaffoldState.bottomSheetState.currentValue}")

                InfoText("targetValue: ${scaffoldState.bottomSheetState.targetValue}")

                InfoText(
                    text = try {
                        "offset: ${scaffoldState.bottomSheetState.requireOffset()}"
                    } catch (e: IllegalStateException) {
                        "offset: (Not Available)"
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch { scaffoldState.bottomSheetState.expand() }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White,
                        )
                    ) {
                        Text("Show BottomSheet")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Message from MainContent!",
                                    actionLabel = "Dismiss"
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White,
                        )
                    ) {
                        Text("Show Snackbar")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoText(
    text: String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.W400,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color
        )
    )

    Spacer(modifier = Modifier.height(4.dp))
}


