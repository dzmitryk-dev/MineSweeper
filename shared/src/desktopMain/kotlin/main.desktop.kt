import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import model.GameMode
import model.GameModelImpl
import ui.Game
import kotlin.random.Random

@Composable fun MainView() {
    val gameModel = GameModelImpl(GameMode.Beginner)
    Game(gameModel.gameState.collectAsState(), gameModel::cellClicked, gameModel::cellMarked)
}

@Preview
@Composable
fun AppPreview() {
    Game(
        GameModelImpl(GameMode.Beginner, Random(42)).gameState.collectAsState(), { _,_ -> }, { _, _ ->}
    )
}