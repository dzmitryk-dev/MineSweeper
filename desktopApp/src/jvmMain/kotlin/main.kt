import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fakes.FakeStringResolver
import model.GameMode
import model.GameModelImpl
import ui.MainView

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MainView(FakeStringResolver::resolveString)
    }
}