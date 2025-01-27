package com.beombeom.composeex.presentation.examples.bottomSheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beombeom.composeex.presentation.common.MainHeader
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ModalBottomSheetEx(title : String) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        stickyHeader {
            MainHeader(title = title)
        }

        item {
            Column(modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                /*
                 * isVisible
                 * BottomSheet가 화면에 표시되고 있는지 여부를 나타냅니다.
                 * sheetState.show() 호출 후, isVisible이 true로 바뀜.
                 * sheetState.hide() 호출 후, isVisible이 false로 바뀜.
                 */
                InfoText("isVisible: ${sheetState.isVisible}")

                /*
                 * currentValue
                 * BottomSheet의 현재 상태를 나타내며, 애니메이션 도중에도 현재 상태를 나타냅니다.
                 * 상태는 Hidden, Expanded, 또는 PartiallyExpanded 등으로 표현됩니다.
                 *
                 * 상태 값 설명:
                 * Collapsed: Bottom Sheet가 최소한의 높이로 축소된 상태.
                 * Expanded: Bottom Sheet가 전체 높이로 확장된 상태.
                 * PartiallyExpanded: Bottom Sheet가 부분적으로 확장된 상태.
                 */
                InfoText("currentValue: ${sheetState.currentValue}")

                /*
                 * targetValue
                 * BottomSheet가 애니메이션 도중에 도달하려는 목표 상태를 나타냅니다.
                 * 애니메이션 중에 상태 변화가 일어나고 있다면, 이 값은 최종 목표 상태를 반환합니다.
                 * 상태는 Hidden, Expanded 으로만 표현됩니다.
                 */
                InfoText("targetValue: ${sheetState.targetValue}")

                /*
                 * requireOffset()
                 * BottomSheet가 확장된 상태에서의 높이를 반환합니다.
                 * BottomSheet가 아직 확장되지 않은 상태라면 IllegalStateException이 발생합니다.
                 */
                InfoText(
                    text = try {
                        "offset: ${sheetState.requireOffset()}"
                    } catch (e: IllegalStateException) {
                        "offset: (Not Available)"
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*
                 * Show Bottom Sheet 버튼
                 * BottomSheet를 화면에 표시합니다.
                 */
                Button(
                    onClick = {
                        coroutineScope.launch { sheetState.show() }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    )
                ) {
                    Text("Show Bottom Sheet")
                }

                /*
                 * Collapsed Bottom Sheet 버튼
                 * BottomSheet를 최소화된 상태로 변경합니다.
                 * 이 기능을 사용하려면 skipPartiallyExpanded = false로 설정해야 합니다.
                 */
                Button(
                    onClick = {
                        coroutineScope.launch {
                            sheetState.partialExpand()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text("Collapsed Bottom Sheet")
                }
            }
        }

        // ModalBottomSheet 섹션
        item {
            if (sheetState.isVisible) {
                /*
                 * ModalBottomSheet
                 * BottomSheet의 내용과 동작을 정의합니다.
                 * sheetState가 변화하면 해당 내용도 업데이트됩니다.
                 */
                ModalBottomSheet(
                    onDismissRequest = {
                        coroutineScope.launch { sheetState.hide() }
                    },
                    sheetState = sheetState,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 16.dp
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(16.dp))

                            /*
                             * BottomSheet 내용
                             * 현재 상태와 목표 상태를 표시합니다.
                             */
                            InfoText("This is BottomSheet Content")
                            InfoText("currentValue: ${sheetState.currentValue}")
                            InfoText("targetValue: ${sheetState.targetValue}")

                            /*
                             * BottomSheet의 확장 상태에서 높이를 반환합니다.
                             * 확장되지 않은 상태에서는 에러 메시지를 표시합니다.
                             */
                            InfoText(
                                text = try {
                                    "offset: ${sheetState.requireOffset()}"
                                } catch (e: IllegalStateException) {
                                    "offset: (Not Available)"
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            /*
                             * Hide Bottom Sheet 버튼
                             * BottomSheet를 닫습니다.
                             */
                            Button(onClick = {
                                coroutineScope.launch { sheetState.hide() }
                            }) {
                                Text("Hide Bottom Sheet")
                            }
                        }
                    }
                }
            }
        }
    }

}


