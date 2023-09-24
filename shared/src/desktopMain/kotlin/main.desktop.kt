import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.flowOf
import model.GameMode
import model.GameModelImpl
import model.GameState
import ui.Game

@Composable fun MainView() = Game(GameModelImpl(GameMode.Beginner).gameState.collectAsState())

@Preview
@Composable
fun AppPreview() {
    Game(flowOf(GameState.EMPTY).collectAsState(GameState.EMPTY))
}