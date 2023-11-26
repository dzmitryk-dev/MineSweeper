package ui

import StrResId
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import currentTimeMillis
import model.AppState
import model.GameModelImpl
import kotlin.random.Random

@Composable
fun MainView(
    stringResolver: (StrResId) -> String
) {
    val appState = remember { mutableStateOf<AppState>(AppState.SelectScreen) }
    appState.value.let { state ->
        when(state) {
            AppState.SelectScreen -> Start(stringResolver) {
                gameMode ->
                val gameModel = GameModelImpl(gameMode = gameMode, random = Random(currentTimeMillis()))
                appState.value = AppState.GameScreen(gameModel)
            }
            is AppState.GameScreen -> with(state.gameModel) {
                Game(
                    gameState = gameState.collectAsState(),
                    onClick = ::cellClicked,
                    onLongClick = ::cellMarked,
                    onRestart = ::restart)
            }

        }
    }
}
