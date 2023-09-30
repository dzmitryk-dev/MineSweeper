package model

import kotlin.test.Test
import kotlin.test.assertEquals

class GetMinesCountTest {

    @Test
    fun testGetMinesCount() {
        listOf(
            GameMode.Beginner to 10,
            GameMode.Expert to 40,
            GameMode.Intermediate to 99,
            GameMode.Custom(10, 10, 42) to 42
        ).forEach { (input, expected) ->
            assertEquals(expected, getMinesCount(input))
        }
    }
}