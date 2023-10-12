package model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class OpenCellTest {

    @Test
    fun should_generate_new_state_object() {
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

    @Test
    fun should_start_game() {
        val testState = GameState(
            gameStatus = GameState.GameStatus.NOT_STARTED,
            flagsCount = 10,
            gameField = GameField.createGameField(
                listOf(
                    listOf(
                        Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                        Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                        Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty)
                    )
                )
            )
        )
        val newState = openCell(testState, 0, 1)
        assertEquals(GameState.GameStatus.IN_PROGRESS, newState.gameStatus)
    }

    @Test
    fun should_over_game_if_open_mine() {
        val testState = GameState(
            gameStatus = GameState.GameStatus.NOT_STARTED,
            flagsCount = 10,
            gameField = GameField.createGameField(
                listOf(
                    listOf(
                        Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                        Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                        Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty)
                    )
                )
            )
        )
        val newState = openCell(testState, 0, 0)
        assertEquals(GameState.GameStatus.GAME_OVER, newState.gameStatus)
    }

    @Test
    fun should_open_cell_if_contains_number() {
        val testState = GameState(
            gameStatus = GameState.GameStatus.NOT_STARTED,
            flagsCount = 10,
            gameField = GameField.createGameField(
                listOf(
                    listOf(
                        Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                        Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                        Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty)
                    )
                )
            )
        )
        val newState = openCell(testState, 0, 1)
    }
}