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

    @Test
    fun should_open_all_mines_if_mine_is_opened() {
        val newState = checkGameState(GameState(
            gameStatus = GameState.GameStatus.IN_PROGRESS,
            gameField = GameField.createGameField(
                listOf(
                    listOf(
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    ),
                    listOf(
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(1)),
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(2)),
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(1)),
                    ),
                    listOf(
                        Cell(Cell.CellState.OPEN, Cell.CellValue.Mine),
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(2)),
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
                    ),
                )
            ),
            flagsCount = 2
        ))

        assertEquals(listOf(
            listOf(
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(1)),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(2)),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(1)),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Mine),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(2)),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Mine),
            ),
        ), newState.gameField)

        assertEquals(GameState.GameStatus.GAME_OVER, newState.gameStatus)
    }

    @Test
    fun should_flag_all_mines_if_only_mines_left() {
        val newState = checkGameState(GameState(
            gameStatus = GameState.GameStatus.IN_PROGRESS,
            gameField = GameField.createGameField(
                listOf(
                    listOf(
                        Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                        Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                        Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                    ),
                    listOf(
                        Cell(Cell.CellState.OPEN, Cell.CellValue.Value(1)),
                        Cell(Cell.CellState.OPEN, Cell.CellValue.Value(2)),
                        Cell(Cell.CellState.OPEN, Cell.CellValue.Value(1)),
                    ),
                    listOf(
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
                        Cell(Cell.CellState.OPEN, Cell.CellValue.Value(2)),
                        Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
                    ),
                )
            ),
            flagsCount = 2
        ))

        assertEquals(listOf(
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Value(1)),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Value(2)),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Value(1)),
            ),
            listOf(
                Cell(Cell.CellState.FLAGGED, Cell.CellValue.Mine),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Value(2)),
                Cell(Cell.CellState.FLAGGED, Cell.CellValue.Mine),
            ),
        ), newState.gameField)
        assertEquals(0, newState.flagsCount)
        assertEquals(GameState.GameStatus.WIN, newState.gameStatus)
    }
}