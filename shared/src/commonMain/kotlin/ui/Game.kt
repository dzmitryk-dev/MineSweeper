@file:OptIn(ExperimentalFoundationApi::class)

package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.shared.generated.resources.Res
import kotlinproject.shared.generated.resources.bomb
import kotlinproject.shared.generated.resources.flag
import kotlinx.coroutines.delay
import model.Cell
import model.GameState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun Game(
    gameState: State<GameState>,
    onRestart: () -> Unit,
    onClick: (Int, Int) -> Unit,
    onLongClick: (Int, Int) -> Unit
) {
    val time = remember { mutableStateOf(0L) }
    val state = remember { gameState }

    Column(
        Modifier.fillMaxSize().background(color = Color.Gray).padding(all = 8.dp),
    ) {
        Row(Modifier.fillMaxWidth().padding(all = 4.dp)) {
            Text(
                modifier = Modifier.background(color = Color.Black)
                    .padding(4.dp)
                    .weight(weight = 1.0f)
                    .align(Alignment.CenterVertically),
                text = String.format("%03d", state.value.flagsCount),
                textAlign = TextAlign.Center,
                color = Color.Red
            )
            Spacer(modifier = Modifier.weight(2.0f))
            Button(modifier = Modifier, onClick = onRestart) {
                Text(
                    text = when (gameState.value.gameStatus) {
                        GameState.GameStatus.NOT_STARTED -> "\uD83D\uDE10"
                        GameState.GameStatus.IN_PROGRESS -> "\uD83D\uDE42"
                        GameState.GameStatus.GAME_OVER -> "\uD83D\uDE2D"
                        GameState.GameStatus.WIN -> "\uD83D\uDE0E"
                    },
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.weight(2.0f))
            Text(
                modifier = Modifier.background(color = Color.Black)
                    .padding(4.dp)
                    .weight(weight = 1.0f, fill = true)
                    .align(Alignment.CenterVertically),
                text = String.format("%03d", time.value),
                maxLines = 1,
                textAlign = TextAlign.Center,
                color = Color.Red
            )
        }
        Field(gameState.value.gameField, onClick, onLongClick)
    }

    LaunchedEffect(gameState.value.gameStatus) {
        while (gameState.value.gameStatus == GameState.GameStatus.IN_PROGRESS) {
            delay(1.toDuration(DurationUnit.SECONDS))
            time.value += 1
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun Field(
    field: List<List<Cell>>,
    onPrimaryClick: (Int, Int) -> Unit,
    onSecondaryClick: (Int, Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.Gray)) {
        for ((x, row) in field.withIndex()) {
            Row(
                modifier = Modifier.fillMaxWidth().weight(weight = 1.0f)) {
                for ((y, e) in row.withIndex()) {
                    val boxSize = remember { mutableStateOf(IntSize.Zero) }
                    Box(
                        Modifier.weight(weight = 1.0f)
                            .fillMaxHeight()
                            .padding(1.dp)
                            .align(Alignment.CenterVertically)
                            .background(color = Color.LightGray)
                            .onSizeChanged { size -> boxSize.value = size }
                    ) {
                        when (e.state) {
                            Cell.CellState.CLOSED -> Surface(
                                modifier = Modifier.fillMaxSize().setupClickListeners(
                                    onPrimaryClick = { onPrimaryClick(x, y) },
                                    onSecondaryClick = { onSecondaryClick(x, y) }),
                                color = MaterialTheme.colors.primary,
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            ) { }

                            Cell.CellState.FLAGGED -> Surface(
                                modifier = Modifier.fillMaxSize().setupClickListeners(
                                    onPrimaryClick = { onPrimaryClick(x, y) },
                                    onSecondaryClick = { onSecondaryClick(x, y) }),
                                color = MaterialTheme.colors.primary,
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            ) {
                                Image(
                                    painterResource(Res.drawable.flag),
                                    null
                                )
                            }

                            Cell.CellState.OPEN -> when (e.value) {
                                Cell.CellValue.Empty -> Spacer(
                                    modifier = Modifier.fillMaxSize()
                                        .background(Color.Yellow)
                                )

                                Cell.CellValue.Mine -> Image(
                                    modifier = Modifier.fillMaxSize()
                                        .align(Alignment.Center)
                                        .run {
                                            if (e.isClicked) {
                                                this.background(color = Color.Red)
                                            } else {
                                                this
                                            }
                                        },
                                    painter = painterResource(Res.drawable.bomb),
                                    contentDescription = null
                                )

                                is Cell.CellValue.Value -> Text(
                                    modifier = Modifier.fillMaxWidth()
                                        .align(Alignment.Center),
                                    text = "${e.value.number}",
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    // Hardcode fo now because the previous way with calculation it in runtime does not work in Android
                                    fontSize = calculateFontSize(boxSize.value)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

expect fun Modifier.setupClickListeners(
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
): Modifier

@Composable
private fun calculateFontSize(elementSize: IntSize): TextUnit {
    return with(LocalDensity.current) {
        (elementSize.height.toDp() - 4.dp).toSp()
    }
}