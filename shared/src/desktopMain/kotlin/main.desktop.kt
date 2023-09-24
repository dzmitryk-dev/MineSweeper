import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import ui.App
import ui.StubHello

@Composable fun MainView() = StubHello()

@Preview
@Composable
fun AppPreview() {
    App()
}