import android.content.Context
import com.myapplication.common.R

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

class AndroidStringResolver(
    private val context: Context
) {
    fun resolveString(strResId: StrResId): String =
        when(strResId) {
            StrResId.Beginner -> R.string.beginner
            StrResId.Medium -> R.string.medium
            StrResId.Expert -> R.string.expert
            StrResId.Custom -> R.string.custom
        }.let { context.getString(it) }
}
