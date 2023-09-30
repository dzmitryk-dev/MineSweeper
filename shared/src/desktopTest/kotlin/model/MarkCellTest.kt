package model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class MarkCellTest {

    @Test
    fun should_update_state_to_flagged() {
        val testGameState = generateTestGameField(Cell.CellState.CLOSED)
        val expected = generateTestGameField(Cell.CellState.FLAGGED)

        val newGameState = markCell(testGameState, 0, 0)

        assertEquals(expected.gameField, newGameState.gameField)
    }

    @Test
    fun should_update_state_to_closed() {
        val testGameState = generateTestGameField(Cell.CellState.FLAGGED)
        val expected = generateTestGameField(Cell.CellState.CLOSED)

        val newGameState = markCell(testGameState, 0, 0)

        assertEquals(expected.gameField, newGameState.gameField)
    }

    @Test
    fun should_not_happes() {
        val testGameState = generateTestGameField(Cell.CellState.OPEN)
        assertFails { markCell(testGameState, 0, 0) }
    }

    private fun generateTestGameField(stateToTest: Cell.CellState): GameState =
        GameState(
            isActive = false,
            flagsCount = 10,
            gameField = GameField.createGameField(
                listOf(listOf(Cell(state = stateToTest, value = Cell.CellValue.Mine)))
            )
        )

}