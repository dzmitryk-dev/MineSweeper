import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

expect fun currentTimeMillis(): Long

@Composable
internal fun Dp.dpToSp(): TextUnit {
    return (this.value * LocalDensity.current.density / LocalDensity.current.fontScale).sp
}