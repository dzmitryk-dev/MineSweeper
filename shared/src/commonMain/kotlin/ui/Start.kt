package ui

import StrResId
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import model.GameMode

@Composable
fun Start(
    stringResolver: (StrResId) -> String,
    gameModeSelectListener: (GameMode) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            arrayOf(GameMode.Beginner, GameMode.Intermediate, GameMode.Expert).map { gameMode ->
                Button(
                    onClick = { gameModeSelectListener(gameMode) }
                ) {
                    Text(text = gameMode.asString(stringResolver))
                }
            }
        }
    }
}

internal fun GameMode.asString(stringResolver: (StrResId) -> String): String =
    stringResolver(
        when (this) {
            GameMode.Beginner -> StrResId.Beginner
            GameMode.Intermediate -> StrResId.Medium
            GameMode.Expert -> StrResId.Expert
            is GameMode.Custom -> StrResId.Custom
        }
    )