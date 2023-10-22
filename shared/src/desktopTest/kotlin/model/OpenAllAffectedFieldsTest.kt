package model

import kotlin.test.Test
import kotlin.test.assertContentEquals

class OpenAllAffectedFieldsTest {

    @Test
    fun findEmptyPointsAround() {
        /*
         * 0 0 0 0 0
         * 0 1 1 1 0
         * 0 1 M 1 0
         * 0 1 1 1 0
         * 0 0 0 0 0
         */
        val gameField = GameField.createGameField(
            listOf(
                listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty)
                ),
                listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty)
                ),
                listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty)
                ),
                listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty)
                ),
                listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty)
                ),
            )
        )

        val expectedGameField = GameField.createGameField(
            listOf(
                listOf(
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty)
                ),
                listOf(
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty)
                ),
                listOf(
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Mine),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty)
                ),
                listOf(
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Value(1)),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty)
                ),
                listOf(
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty)
                ),
            )
        )

//        val points = gameField.openAllAffectedFields(0, 0)
//        assertContentEquals(expectedGameField, gameField)
    }
}