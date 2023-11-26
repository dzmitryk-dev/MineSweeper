import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import fakes.FakeStringResolver
import model.GameMode
import model.GameModelImpl
import ui.Game
import ui.Start
import kotlin.random.Random

@Preview
@Composable
internal fun GamePreview() {
    Game(
        gameState = GameModelImpl(GameMode.Beginner, Random(42)).gameState.collectAsState(),
        onClick = { _,_ -> },
        onLongClick = { _, _ -> },
        onRestart = { }
    )
}

@Preview
@Composable
internal fun StartPreview() {
    Start(FakeStringResolver::resolveString) {  }
}