package com.beombeom.composeex.presentation.examples.sideEffect

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.UUID


@Composable
fun SideEffectEx() {
    var showGreeting by remember { mutableStateOf(true) }
    var uniqueId by remember { mutableStateOf(generateRandomString()) }
    val count = remember { mutableStateOf(0) }

    Column {
        Button(onClick = {
            count.value += 1
            Log.d("SideEffectEx", "Count button clicked, count = ${count.value}")
        }) {
            Text("Count is ${count.value}")
        }

        if (showGreeting) {
            ComponentSample(uniqueId) // Displays LaunchedEffect and DisposableEffect behavior
        }

        Button(onClick = {
            showGreeting = !showGreeting
            Log.d("SideEffectEx", "Toggle Greeting button clicked, showGreeting = $showGreeting")
        }) {
            Text(if (showGreeting) "Hide Greeting" else "Show Greeting")
        }

        Button(onClick = {
            uniqueId = generateRandomString()
            Log.d("SideEffectEx", "Change ID button clicked, new uniqueId = $uniqueId")
        }) {
            Text("Generate New ID")
        }
    }
}

@Composable
fun ComponentSample(uniqueId: String) {
    Text(text = "Hello $uniqueId!")

    // Example: `LaunchedEffect`
    LaunchedEffect(uniqueId) {
        Log.d("ComponentSample", "LaunchedEffect triggered by randomId = $uniqueId")
    }

    // Example: `DisposableEffect`
    DisposableEffect(key1 = uniqueId) {
        Log.d("ComponentSample", "DisposableEffect started for randomId = $uniqueId")

        onDispose {
            Log.d("ComponentSample", "DisposableEffect disposed for randomId = $uniqueId")
        }
    }

    // Example: `SideEffect`
    SideEffect {
        Log.d("ComponentSample", "SideEffect: ComponentSample recomposed with randomId = $uniqueId")
    }
}

fun generateRandomString(): String {
    return UUID.randomUUID().toString()
}
