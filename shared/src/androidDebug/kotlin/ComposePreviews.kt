import androidx.compose.runtime.Composable
import fakes.FakeStringResolver
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.Start

@Preview
@Composable
internal fun StartPreview() {
    Start(FakeStringResolver::resolveString) { }
}

