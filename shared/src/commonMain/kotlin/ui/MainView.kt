package ui

import StrResId
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import currentTimeMillis
import model.GameModelImpl
import kotlin.random.Random

//@Composable
//fun MainView(stringResolver: (StrResId) -> String) {
//    Start(stringResolver) { gameMode -> GameModelImpl(gameMode = gameMode, random = Random(currentTimeMillis())) }
//    Game(
//        gameState = gameModel.gameState.collectAsState(),
//        onClick = gameModel::cellClicked,
//        onLongClick = gameModel::cellMarked,
//        onRestart = gameModel::restart)
//}