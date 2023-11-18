package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.onClick
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
actual fun Modifier.setupClickListeners(
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
): Modifier =
    onClick(
        onClick = onPrimaryClick
    ).onClick(
        matcher = PointerMatcher.mouse(PointerButton.Secondary),
        onClick = onSecondaryClick
    )

actual fun calculateFontSize(elementSize: IntSize): TextUnit {
    return (elementSize.height.dp - 2.dp).value.sp
}