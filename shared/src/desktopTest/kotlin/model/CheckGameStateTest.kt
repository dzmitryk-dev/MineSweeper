package model

import org.junit.Test
import kotlin.test.assertEquals

class CheckGameStateTest {

    private val testState = GameState(
        gameStatus = GameState.GameStatus.NOT_STARTED,
        gameField = GameField.createGameField(
            listOf(listOf(
                Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine)
            ))
        ),
        flagsCount = 1
    )

    @Test
    fun should_change_game_status_to_in_progress() {
        val newState = checkGameState(testState)

        assertEquals(GameState.GameStatus.IN_PROGRESS, newState.gameStatus)
    }

    @Test
    fun should_over_game_if_mine_opened() {
        val newState = checkGameState(GameState(
            gameStatus = GameState.GameStatus.IN_PROGRESS,
            gameField = GameField.createGameField(
                listOf(listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Mine)
                ))
            ),
            flagsCount = 1
        ))

        assertEquals(GameState.GameStatus.GAME_OVER, newState.gameStatus)
    }
}