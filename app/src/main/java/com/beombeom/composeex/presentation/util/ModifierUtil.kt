package com.beombeom.composeex.presentation.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    composed {
        clickable(
            onClick = onClick,
            // Ripple Effect 제거
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        )
    }
