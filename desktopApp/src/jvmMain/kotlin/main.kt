import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.GameMode
import model.GameModelImpl

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val gameModel = GameModelImpl(GameMode.Expert)
        MainView(gameModel)
    }
}