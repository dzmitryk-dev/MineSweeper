import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import fakes.FakeStringResolver
import model.GameModel
import ui.Game
import ui.Start

@Composable fun MainView(gameModel: GameModel) =
    Game(
        gameState = gameModel.gameState.collectAsState(),
        onClick = gameModel::cellClicked,
        onLongClick = gameModel::cellMarked,
        onRestart = gameModel::restart
    )
