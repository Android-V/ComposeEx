package com.beombeom.composeex.presentation.examples.sideEffect

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beombeom.composeex.presentation.MainHeader
import com.beombeom.composeex.presentation.examples.bottomSheet.InfoText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SideEffectEx(title : String) {
    var showGreeting by rememberSaveable { mutableStateOf(true) }
    var uniqueId by rememberSaveable { mutableStateOf(generateRandomString()) }
    val count = rememberSaveable { mutableStateOf(0) }

    // produceState: 비동기 데이터를 생성
    val asyncData by produceState(initialValue = "No ID yet...", uniqueId) {
        delay(2000) //초기값을 보여주기위한 의도적인 딜레이
        value = "ID (asyncData): $uniqueId"
    }

    val coroutineScope = rememberCoroutineScope { Dispatchers.IO }
    val greetingState = rememberUpdatedState(if (showGreeting) "Hide My ID" else "Show My ID")
    val derivedCount = remember { derivedStateOf { "Derived Count: ${count.value * 2}" } }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            MainHeader(title = title)

            // Count 관리
            Button(onClick = {
                count.value += 1
                Log.d("SideEffectEx", "Count button clicked, count = ${count.value}")
            }) {
                Text("Count is ${count.value}")
            }
            InfoText(text = derivedCount.value)

            Spacer(modifier = Modifier.height(10.dp))

            // snapshotFlow: Count 상태 관찰
            LaunchedEffect(Unit) {
                snapshotFlow { count.value }.collect { value ->
                    Log.d("SideEffectEx", "snapshotFlow emitted value: $value")
                }
            }

            Box(modifier = Modifier.height(20.dp), contentAlignment = Alignment.Center) {
            // ID 관련 SideEffect
            if (showGreeting) {
                    IDSideEffects(uniqueId = uniqueId)
                }
            }

            // ID 변경 및 토글
            Button(onClick = {
                showGreeting = !showGreeting
                Log.d("SideEffectEx", "Toggle Greeting button clicked, showGreeting = $showGreeting")
            }) {
                Text(greetingState.value)
            }
            Button(onClick = {
                uniqueId = generateRandomString()
                Log.d("SideEffectEx", "Change ID button clicked, new uniqueId = $uniqueId")
            }) {
                Text("Generate New ID")
            }

            // Coroutine 실행
            Button(onClick = {
                coroutineScope.launch {
                    delay(1000)
                    Log.d("SideEffectEx", "Coroutine executed with uniqueId = $uniqueId")
                }
            }) {
                Text("Launch Coroutine")
            }

            // produceState 데이터 표시
            InfoText(text = asyncData)
        }
    }
}

@Composable
fun IDSideEffects(uniqueId: String) {
    InfoText(text = "My ID: $uniqueId!")

    // LaunchedEffect: 키가 변경될 때마다 실행
    LaunchedEffect(uniqueId) {
        Log.d("SideEffectEx", "LaunchedEffect triggered by randomId = $uniqueId")
    }

    // DisposableEffect: 키가 변경되거나 컴포저블이 제거될 때 실행 및 정리
    DisposableEffect(key1 = uniqueId) {
        Log.d("SideEffectEx", "DisposableEffect started for randomId = $uniqueId")

        onDispose {
            Log.d("SideEffectEx", "DisposableEffect disposed for randomId = $uniqueId")
        }
    }

    // SideEffect: 컴포저블이 재구성될 때마다 실행
    SideEffect {
        Log.d("SideEffectEx", "SideEffect: ComponentSample recomposed with randomId = $uniqueId")
    }
}

fun generateRandomString(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..8)
        .map { chars.random() }
        .joinToString("")
}

