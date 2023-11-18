package model

import kotlin.random.Random
import kotlin.test.*

class GameFieldTest {

    @Test
    fun testGenerateGameField() {
        val result = generateGameField(GameMode.Custom(3, 3, 2), Random(42L))

        fun map(v: Cell.CellValue): String =
            when (v) {
                Cell.CellValue.Empty -> "0"
                Cell.CellValue.Mine -> "M"
                is Cell.CellValue.Value -> v.number.toString()
            }

        val mappedResult = result.map { l ->
            l.map { e -> map(e.value) }
        }

        println("*********************")
        println("Result: ")
        mappedResult.forEach { l ->
            println(l.joinToString(separator = "|") { it })
        }
        println("*********************")


        val expected: List<List<String>> = buildList {
            add(listOf("0", "1", "M"))
            add(listOf("0", "2", "2"))
            add(listOf("0", "1", "M"))
        }

        assertContentEquals(expected, mappedResult)
    }

    @Test
    fun testCalculateMinesAround() {
        val minesSet = setOf(createPoint(0, 2), createPoint(2, 2))

        assertEquals(0, calculateMinesAround(createPoint(0, 0), minesSet))
        assertEquals(0, calculateMinesAround(createPoint(1, 0), minesSet))
        assertEquals(0, calculateMinesAround(createPoint(2, 0), minesSet))
        assertEquals(1, calculateMinesAround(createPoint(0, 1), minesSet))
        assertEquals(2, calculateMinesAround(createPoint(1, 1), minesSet))
        assertEquals(1, calculateMinesAround(createPoint(2, 1), minesSet))
        assertEquals(2, calculateMinesAround(createPoint(1, 2), minesSet))
    }

    @Test
    fun testGameFieldUpdate() {
        val testGameField =
            GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty))))

        val newGameField = testGameField.updateField {
            this[0][0] = this[0][0].copy(state = Cell.CellState.OPEN)
        }

        assertNotEquals(testGameField, newGameField, "$testGameField and $newGameField")
    }

    @Test
    fun shouldBeEqual() {
        val field1 =
            GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty))))
        val field2 =
            GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty))))

        assertEquals(field1, field2)
    }

    @Test
    fun shouldNotBeEqual() {
        val field1 =
            GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty))))
        val field2 =
            GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty))))

        assertNotEquals(field1, field2)
    }

    @Test
    fun testMarkCellFlaggedToClosed() {
        // Create a GameField with a flagged cell
        val initialField = GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.FLAGGED, value = Cell.CellValue.Empty))))

        // Perform the action
        val updatedField = initialField.markCell(0, 0)

        // Assert that the cell is now closed
        assertEquals(Cell.CellState.CLOSED, updatedField[0][0].state)
    }

    @Test
    fun testMarkCellClosedToFlagged() {
        // Create a GameField with a closed cell
        val initialField = GameField.createGameField(listOf(listOf(Cell(Cell.CellState.CLOSED, value = Cell.CellValue.Empty))))

        // Perform the action
        val updatedField = initialField.markCell(0, 0)

        // Assert that the cell is now flagged
        assertEquals(Cell.CellState.FLAGGED, updatedField[0][0].state)
    }

    @Test(expected = IllegalStateException::class)
    fun testMarkCellInvalidState() {
        // Create a GameField with an invalid state (not CLOSED or FLAGGED)
        val initialField = GameField.createGameField(listOf(listOf(Cell(Cell.CellState.OPEN, value = Cell.CellValue.Empty))))

        // This should throw an IllegalStateException
        initialField.markCell(0, 0)
    }

    @Test
    fun testOpenCellWithEmptyCell() {
        val initialField = GameField.createGameField(
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
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(2)),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
                ),
            )
        )

        // Perform the action
        val updatedField = initialField.openCell(0, 0)

        // Assert that the cell is now open
        assertEquals(listOf(
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty, isClicked = true),
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
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(2)),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
            ),
        ), updatedField)
    }

    @Test
    fun testOpenCellWithNonEmptyCell() {
        val initialField = GameField.createGameField(
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
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(2)),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
                ),
            )
        )

        // Perform the action
        val updatedField = initialField.openCell(1, 1)

        // Assert that the cell is now open
        assertEquals(listOf(
            listOf(
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(1)),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Value(2), isClicked = true),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(1)),
            ),
            listOf(
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Value(2)),
                Cell(Cell.CellState.CLOSED, Cell.CellValue.Mine),
            ),
        ), updatedField)
    }

    @Test
    fun testOpenFullyEmptyField() {
        val initialField = GameField.createGameField(
            listOf(
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
            )
        )

        // Perform the action
        val updatedField = initialField.openCell(0, 0)

        // Assert that the cell is now open
        assertEquals(listOf(
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty, isClicked = true),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
        ), updatedField)
    }

    @Test
    fun testOpenFullyEmptyNonSquereField() {
        val initialField = GameField.createGameField(
            listOf(
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
            )
        )

        // Perform the action
        val updatedField = initialField.openCell(0, 0)

        // Assert that the cell is now open
        assertEquals(listOf(
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty, isClicked = true),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
        ), updatedField)
    }

    @Test
    fun testOpenFullyEmptyNonSquereField2() {
        val initialField = GameField.createGameField(
            listOf(
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
                listOf(
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                    Cell(Cell.CellState.CLOSED, Cell.CellValue.Empty),
                ),
            )
        )

        // Perform the action
        val updatedField = initialField.openCell(0, 0)

        // Assert that the cell is now open
        assertEquals(listOf(
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty, isClicked = true),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
            listOf(
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
                Cell(Cell.CellState.OPEN, Cell.CellValue.Empty),
            ),
        ), updatedField)
    }
}