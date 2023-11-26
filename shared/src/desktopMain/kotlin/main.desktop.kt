import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import fakes.FakeStringResolver
import model.GameMode
import model.GameModel
import model.GameModelImpl
import ui.Game
import ui.Start
import kotlin.random.Random

@Composable fun MainView(gameModel: GameModel) {
    Game(
        gameState = gameModel.gameState.collectAsState(),
        onClick = gameModel::cellClicked,
        onLongClick = gameModel::cellMarked,
        onRestart = gameModel::restart)
}

@Preview
@Composable
fun AppPreview() {
    Game(
        gameState = GameModelImpl(GameMode.Beginner, Random(42)).gameState.collectAsState(),
        onClick = { _,_ -> },
        onLongClick = { _, _ -> },
        onRestart = { }
    )
}

@Preview
@Composable
fun StartPreview() {
    val stringResolver = FakeStringResolver()
    Start(stringResolver::resolveString) {  }
}