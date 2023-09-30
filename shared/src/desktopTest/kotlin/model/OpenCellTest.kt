package model

import kotlin.test.Test
import kotlin.test.assertNotEquals

class OpenCellTest {

    @Test
    fun should_generte_new_state_object() {
        val testState = GameState(
            gameStatus = GameState.GameStatus.NOT_STARTED,
            flagsCount = 10,
            gameField = GameField.createGameField(
                listOf(listOf(Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine)))
            )
        )
        val newState = openCell(testState, 0, 0)

        assertNotEquals(testState, newState)
    }
}