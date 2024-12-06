package com.beombeom.composeex.presentation.examples.sideEffect

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import java.util.UUID
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beombeom.composeex.presentation.examples.bottomSheet.InfoText

@Composable
fun SideEffectEx() {
    var showGreeting by rememberSaveable { mutableStateOf(true) }
    var uniqueId by rememberSaveable { mutableStateOf(generateRandomString()) }
    val count = rememberSaveable { mutableStateOf(0) }

    // Example: produceState
    val asyncData by produceState(initialValue = "No ID yet...", uniqueId) {
        delay(2000)
        // 실제로 ID가 없는 것은 아니지만 initialValue를 확인하고자 사용.
        value = "ID shown in asyncData: $uniqueId"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Count Button
            Button(onClick = {
                count.value += 1
                Log.d("SideEffectEx", "Count button clicked, count = ${count.value}")
            }) {
                Text("Count is ${count.value}")
            }

            if (showGreeting) {
                ComponentSample(uniqueId)
            }

            Button(onClick = {
                showGreeting = !showGreeting
                Log.d(
                    "SideEffectEx",
                    "Toggle Greeting button clicked, showGreeting = $showGreeting"
                )
            }) {
                Text(if (showGreeting) "Hide Greeting" else "Show Greeting")
            }

            Button(onClick = {
                uniqueId = generateRandomString()
                Log.d("SideEffectEx", "Change ID button clicked, new uniqueId = $uniqueId")
            }) {
                Text("Generate New ID")
            }

            InfoText(text = asyncData)
        }
    }
}

@Composable
fun ComponentSample(uniqueId: String) {
    InfoText(text = "My Id: $uniqueId!")

    LaunchedEffect(uniqueId) {
        Log.d("ComponentSample", "LaunchedEffect triggered by randomId = $uniqueId")
    }

    DisposableEffect(key1 = uniqueId) {
        Log.d("ComponentSample", "DisposableEffect started for randomId = $uniqueId")

        onDispose {
            Log.d("ComponentSample", "DisposableEffect disposed for randomId = $uniqueId")
        }
    }

    SideEffect {
        Log.d("ComponentSample", "SideEffect: ComponentSample recomposed with randomId = $uniqueId")
    }
}

fun generateRandomString(): String {
    return UUID.randomUUID().toString()
}
