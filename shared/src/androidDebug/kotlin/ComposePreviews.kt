import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fakes.FakeStringResolver
import ui.Start

@Preview
@Composable
internal fun StartPreview() {
    Start(FakeStringResolver::resolveString) { }
}

