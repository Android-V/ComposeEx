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
import com.beombeom.composeex.presentation.examples.bottomSheet.InfoText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun SideEffectEx() {
    // rememberSaveable: 구성 변경이나 프로세스 중단 후에도 상태를 유지
    var showGreeting by rememberSaveable { mutableStateOf(true) }
    var uniqueId by rememberSaveable { mutableStateOf(generateRandomString()) }
    val count = rememberSaveable { mutableStateOf(0) }

    // produceState: 비동기 작업으로 상태를 생성하며 컴포지션 생명주기에 따라 관리
    val asyncData by produceState(initialValue = "No ID yet...", uniqueId) {
        delay(2000)
        value = "ID shown in asyncData: $uniqueId"
    }


    val coroutineScope = rememberCoroutineScope { Dispatchers.IO }

    Button(onClick = {
        coroutineScope.launch {
            Log.d("CustomMainImmediateDispatcher", "Running on Main Immediate Dispatcher")
        }
    }) {
        Text("Launch Immediate Main Coroutine")
    }


    // rememberUpdatedState의 인자 값이 변경되어도 리컴포지션이 발생하지 않는다.
    val greetingState = rememberUpdatedState(if (showGreeting) "Hide My Id" else "Show My Id!")

    // derivedStateOf: 기존 상태를 변환하여 새로운 상태로 제공, 의존성 있는 상태가 변경될 때만 다시 계산
    val derivedCount = remember {
        derivedStateOf { "Derived Count: ${count.value * 2}" }
    }

    // snapshotFlow: Compose 상태를 Flow로 변환하여 비동기적으로 관찰 가능
    val snapshotFlow = snapshotFlow { count.value }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Count 상태를 관리하는 버튼
            Button(onClick = {
                count.value += 1
                Log.d("SideEffectEx", "Count button clicked, count = ${count.value}")
            }) {
                Text("Count is ${count.value}")
            }

            // derivedStateOf를 사용한 상태 표시
            Text(text = derivedCount.value)

            // snapshotFlow를 사용해 count 상태를 관찰
            LaunchedEffect(Unit) {
                snapshotFlow.collect { value ->
                    Log.d("SideEffectEx", "snapshotFlow emitted value: $value")
                }
            }

            // Greeting 상태를 토글하는 버튼
            if (showGreeting) {
                ComponentSample(uniqueId)
            }

            Button(onClick = {
                showGreeting = !showGreeting
                Log.d("SideEffectEx", "Toggle Greeting button clicked, showGreeting = $showGreeting")
            }) {
                Text(greetingState.value)
            }

            // uniqueId를 변경하는 버튼
            Button(onClick = {
                uniqueId = generateRandomString()
                Log.d("SideEffectEx", "Change ID button clicked, new uniqueId = $uniqueId")
            }) {
                Text("Generate New ID")
            }

            // rememberCoroutineScope를 사용해 Coroutine 실행
            Button(onClick = {
                coroutineScope.launch {
                    delay(1000)
                    Log.d("SideEffectEx", "Coroutine executed with uniqueId = $uniqueId")
                  }
            }) {
                Text("Launch Coroutine")
            }

            // produceState로 생성된 비동기 데이터를 표시
            InfoText(text = asyncData)
        }
    }
}

@Composable
fun RememberUpdatedStateExample(onEvent: (String) -> Unit) {
    val updatedOnEvent = rememberUpdatedState(onEvent)

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            updatedOnEvent.value("Event Triggered")
        }
    }
}



@Composable
fun ComponentSample(uniqueId: String) {
    InfoText(text = "My Id: $uniqueId!")

    // LaunchedEffect: 키가 변경될 때마다 실행
    LaunchedEffect(uniqueId) {
        Log.d("ComponentSample", "LaunchedEffect triggered by randomId = $uniqueId")
    }

    // DisposableEffect: 키가 변경되거나 컴포저블이 제거될 때 실행 및 정리
    DisposableEffect(key1 = uniqueId) {
        Log.d("ComponentSample", "DisposableEffect started for randomId = $uniqueId")

        onDispose {
            Log.d("ComponentSample", "DisposableEffect disposed for randomId = $uniqueId")
        }
    }

    // SideEffect: 컴포저블이 재구성될 때마다 실행
    SideEffect {
        Log.d("ComponentSample", "SideEffect: ComponentSample recomposed with randomId = $uniqueId")
    }
}

fun generateRandomString(): String {
    return UUID.randomUUID().toString()
}