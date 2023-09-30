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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
            Button(modifier = Modifier, onClick = { }) {
                Text(text = ":)")
            }
            Spacer(modifier = Modifier.weight(2.0f))
            Text(
                modifier = Modifier.background(color = Color.Black)
                    .padding(4.dp)
                    .weight(weight = 1.0f, fill = true)
                    .align(Alignment.CenterVertically),
                text = String.format("%03d", time.value),
                textAlign = TextAlign.Center,
                color = Color.Red
            )
        }
        Field(gameState.value.gameField, onClick, onLongClick)
    }

    LaunchedEffect("Time") {
        while (gameState.value.isActive) {
            delay(1.toDuration(DurationUnit.SECONDS))
            time.value += 1
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun Field(
    field: List<List<Cell>>,
    onClick: (Int, Int) -> Unit,
    onLongClick: (Int, Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.Gray)) {
        for ((x, row) in field.withIndex()) {
            Row(modifier = Modifier.fillMaxWidth().weight(weight = 1.0f)) {
                for ((y, e) in row.withIndex()) {
                    Box(
                        Modifier.weight(weight = 1.0f)
                            .fillMaxHeight()
                            .padding(1.dp)
                            .align(Alignment.CenterVertically)
                            .background(color = Color.LightGray)
                    ) {
                        when (e.state) {
                            Cell.CellState.CLOSED -> Surface(
                                modifier = Modifier.fillMaxSize().combinedClickable(
                                    onClick = { onClick(x, y) },
                                    onLongClick = { onLongClick(x, y) }
                                ),
                                color = MaterialTheme.colors.primary,
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            ) { }
                            Cell.CellState.FLAGGED -> Surface(
                                modifier = Modifier.fillMaxSize().combinedClickable(
                                    onClick = { onClick(x, y) },
                                    onLongClick = { onLongClick(x, y) }
                                ),
                                color = MaterialTheme.colors.primary,
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            ) {
                                Image(
                                    painterResource("flag.xml"),
                                    null
                                )
                            }
                            Cell.CellState.OPEN -> when (e.value) {
                                Cell.CellValue.Empty -> Spacer(
                                    modifier = Modifier.fillMaxSize()
                                        .background(Color.Yellow)
                                )

                                Cell.CellValue.Mine -> Image(
                                        modifier = Modifier.fillMaxWidth()
                                            .align(Alignment.Center),
                                        painter = painterResource("bomb.xml"),
                                        contentDescription = null
                                    )
                                is Cell.CellValue.Value -> Text(
                                    modifier = Modifier.fillMaxWidth()
                                        .background(Color.Red)
                                        .align(Alignment.Center),
                                    text = "${e.value.number}",
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}