package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
actual fun Modifier.setupClickListeners(
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
): Modifier = combinedClickable(
    onClick = onPrimaryClick,
    onLongClick = onSecondaryClick
)