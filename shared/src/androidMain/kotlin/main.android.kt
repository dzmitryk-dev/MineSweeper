import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import model.GameModel
import ui.Game

@Composable fun MainView(gameModel: GameModel) =
    Game(
        gameState = gameModel.gameState.collectAsState(),
        onClick = gameModel::cellClicked,
        onLongClick = gameModel::cellMarked,
        onRestart = gameModel::restart
    )
