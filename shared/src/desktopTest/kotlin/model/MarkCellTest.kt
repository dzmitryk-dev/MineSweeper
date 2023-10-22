package model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotEquals

class MarkCellTest {

    @Test
    fun should_update_state_to_flagged() {
        val testGameState = generateTestGameField(Cell.CellState.CLOSED, flagsCount = 1)
        val expected = generateTestGameField(Cell.CellState.FLAGGED, flagsCount = 0)

        val newGameState = markCell(testGameState, 0, 0)

        assertEquals(expected.gameField, newGameState.gameField)
        assertEquals(expected.flagsCount, newGameState.flagsCount)
    }

    @Test
    fun should_update_state_to_closed() {
        val testGameState = generateTestGameField(Cell.CellState.FLAGGED, flagsCount = 0)
        val expected = generateTestGameField(Cell.CellState.CLOSED, flagsCount = 1)

        val newGameState = markCell(testGameState, 0, 0)

        assertEquals(expected.gameField, newGameState.gameField)
        assertEquals(expected.flagsCount, newGameState.flagsCount)
    }

    @Test
    fun should_not_happens() {
        val testGameState = generateTestGameField(Cell.CellState.OPEN)
        assertFails { markCell(testGameState, 0, 0) }
    }

    @Test
    fun should_create_new_state_object() {
        val testGameState = generateTestGameField(Cell.CellState.CLOSED)
        val newGameState = markCell(testGameState, 0, 0)

        assertNotEquals(testGameState, newGameState)
    }

    private fun generateTestGameField(stateToTest: Cell.CellState, flagsCount: Int = 1): GameState =
        GameState(
            gameStatus = GameState.GameStatus.NOT_STARTED,
            flagsCount = flagsCount,
            gameField = GameField.createGameField(
                listOf(listOf(Cell(state = stateToTest, value = Cell.CellValue.Mine)))
            )
        )

}