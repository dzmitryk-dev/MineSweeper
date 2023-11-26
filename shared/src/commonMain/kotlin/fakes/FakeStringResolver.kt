package fakes

import StrResId

/**
 * Simple hardcoded strings for development purposes.
 */
object FakeStringResolver {
    fun resolveString(strResId: StrResId): String {
        return when(strResId) {
            StrResId.Beginner -> "Beginner"
            StrResId.Medium -> "Medium"
            StrResId.Expert -> "Expert"
            StrResId.Custom -> "Custom"
        }
    }
}