package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
actual fun Modifier.setupClickListeners(
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
): Modifier = combinedClickable(
    onClick = onPrimaryClick,
    onLongClick = onSecondaryClick
)

actual fun calculateFontSize(elementSize: IntSize): TextUnit {
    return elementSize.height.sp
}