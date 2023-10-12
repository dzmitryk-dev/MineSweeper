package model

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class GenerateGameFieldTest {


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
    fun testGenerateGameField() {
        val result = generateGameField(GameMode.Custom(3, 3, 2), Random(42L))

        fun map(v: Cell.CellValue): String =
            when(v) {
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
            add(listOf("0", "0", "0"))
            add(listOf("1", "2", "1"))
            add(listOf("M", "2", "M"))
        }

        assertContentEquals(expected, mappedResult)
    }

    @Test
    fun testGameFieldUpdate() {
        val testGameField = GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty))))

        val newGameField = testGameField.updateCell(0 ,0 ) { cell ->
            cell.copy(state = Cell.CellState.OPEN)
        }

        assertNotEquals(testGameField, newGameField, "$testGameField and $newGameField")
    }

    @Test
    fun testGameFiledUpdates2() {
        val testGameField = GameField.createGameField(
            listOf(
                listOf(
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty),
                    Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty)
                )
            )
        )

        val newGameField = testGameField.updateCell(
            listOf(
                createPoint(0, 0),
                createPoint(0, 1),
                createPoint(0, 2)
            )
        ) { cell -> cell.copy(state = Cell.CellState.OPEN) }

        assertNotEquals(testGameField, newGameField, "$testGameField and $newGameField")
    }

    @Test
    fun shouldBeEqual() {
        val field1 = GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty))))
        val field2 = GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty))))

        assertEquals(field1, field2)
    }

    @Test
    fun shouldNotBeEqual() {
        val field1 = GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.CLOSED, value = Cell.CellValue.Empty))))
        val field2 = GameField.createGameField(listOf(listOf(Cell(state = Cell.CellState.OPEN, value = Cell.CellValue.Empty))))

        assertNotEquals(field1, field2)
    }

}