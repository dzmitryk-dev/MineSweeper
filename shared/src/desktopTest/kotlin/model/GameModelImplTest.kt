package model

import kotlinx.coroutines.test.runTest
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class GameModelImplTest {

    private val gameModel = GameModelImpl(gameMode = GameMode.Custom(3, 3, 2), Random(42L))

    @Test
    fun getGameState() = runTest {
        val actual = gameModel.gameState.value

        val expected = GameState(
            gameStatus = GameState.GameStatus.NOT_STARTED,
            gameField = buildList {
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                ))
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(2)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                ))
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(2)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                ))
            }.toMutableList()
            .let { GameField.createGameField(it) },
            flagsCount = 2
        )

        assertEquals(expected, actual)
    }

    @Test
    fun cellClicked() {
        gameModel.cellClicked(1, 1)

        val actual = gameModel.gameState.value

        val expected = GameState(
            gameStatus = GameState.GameStatus.IN_PROGRESS,
            gameField = buildList {
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                ))
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(2), isClicked = true),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                ))
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(2)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                ))
            }.toMutableList().let { GameField.createGameField(it) },
            flagsCount = 2
        )

        assertEquals(expected, actual)
    }

    @Test
    fun cellClicked2() {
        gameModel.cellClicked(0, 0)

        val actual = gameModel.gameState.value

        val expected = GameState(
            gameStatus = GameState.GameStatus.IN_PROGRESS,
            gameField = buildList {
                add(mutableListOf(
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty, isClicked = true),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                ))
                add(mutableListOf(
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(2)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                ))
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(2)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                ))
            }.toMutableList().let { GameField.createGameField(it) },
            flagsCount = 2
        )

        assertEquals(expected, actual)
    }

    @Test
    fun cellClicked3() {
        gameModel.cellClicked(2, 2)

        val actual = gameModel.gameState.value

        val expected = GameState(
            gameStatus = GameState.GameStatus.GAME_OVER,
            gameField = buildList {
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                ))
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(2)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                ))
                add(mutableListOf(
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Mine),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(2)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Mine, isClicked = true),
                ))
            }.toMutableList().let { GameField.createGameField(it) },
            flagsCount = 2
        )

        assertEquals(expected, actual)
    }

    @Test
    fun cellMarked() {
        gameModel.cellMarked(1, 1)

        val actual = gameModel.gameState.value

        val expected = GameState(
            gameStatus = GameState.GameStatus.IN_PROGRESS,
            gameField = listOf(
                listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                ),
                listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.FLAGGED, value = Cell.CellValue.Value(2)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                ),
                listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(2)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                )
            ).let { GameField.createGameField(it) },
            flagsCount = 1
        )

        assertEquals(expected, actual)
    }

    @Test
    fun restart() {
        val initialState = gameModel.gameState.value

        gameModel.restart()

        val newState = gameModel.gameState.value

        assertNotEquals(initialState, newState)
    }
}