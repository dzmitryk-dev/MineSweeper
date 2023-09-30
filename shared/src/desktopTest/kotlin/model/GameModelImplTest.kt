package model

import kotlinx.coroutines.test.runTest
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class GameModelImplTest {

    private val gameMode = GameModelImpl(gameMode = GameMode.Custom(3, 3, 2), Random(42L))

    @Test
    fun getGameState() = runTest {
        val actual = gameMode.gameState.value

        val expected = GameState(
            isActive = false,
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
    }

    @Test
    fun cellMarked() {
        gameMode.cellMarked(1, 1)

        val actual = gameMode.gameState.value

        val expected = GameState(
            isActive = false,
            gameField = buildList {
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                ))
                add(mutableListOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.FLAGGED, value = Cell.CellValue.Value(2)),
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
}