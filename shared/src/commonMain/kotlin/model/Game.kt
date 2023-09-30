package model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.random.Random

data class GameState(
    val isActive: Boolean,
    val gameField: GameField,
    val flagsCount: Int,
)

data class Cell(
    val state: CellState,
    val value: CellValue
) {

    sealed class CellValue {
        data object Mine : CellValue()
        data object Empty : CellValue()
        data class Value(val number: Int) : CellValue()
    }

    enum class CellState {
        CLOSED, FLAGGED, OPEN
    }
}


/**
 * Begin - 9x9, 10 mines
 * Intermediate - 16x16, 40 mines
 * Expert - 16x30, 99 mines
 * Custom - Any, Any
 */
sealed class GameMode(open val fieldWidth: Int, open val fieldHeight: Int, open val minesCount: Int) {
    data object Beginner : GameMode(9, 9, 10)
    data object Intermediate : GameMode(16, 16, 40)
    data object Expert : GameMode(16, 30, 99)
    data class Custom(
        override val fieldWidth: Int,
        override val fieldHeight: Int,
        override val minesCount: Int
    ) : GameMode(
        fieldWidth, fieldHeight, minesCount
    )
}

typealias Point = Pair<Int, Int>

val Point.x
    get() = this.first
val Point.y
    get() = this.second

internal fun createPoint(x: Int, y: Int): Point = Pair(x, y)

internal fun calculateMinesAround(p: Point, minesCords: Set<Point>): Int {
    val pointsToCheck = buildSet {
        (p.x - 1..p.x + 1).forEach { x ->
            (p.y - 1..p.y + 1).forEach { y ->
                if (!(x == p.x && y == p.y)) {
                    add(createPoint(x, y))
                }
            }
        }
    }
    return pointsToCheck.count { it in minesCords }
}

/**
 * This class wraps and encapsulates all operations with game field collection under the hood
 */
data class GameField internal constructor(
    private val _field: ImmutableList<ImmutableList<Cell>>
): List<List<Cell>> by _field {
    fun updateCell(row: Int, column: Int, updateFunc: (Cell) -> Cell): GameField {
        val oldValue = _field[row][column]
        val newValue = updateFunc(oldValue)

         val newField = _field.toMutableList().apply {
             this[row] = this[row].toMutableList().apply {
                this[column] = newValue
            }.toImmutableList()
        }
        return createGameField(newField)
    }

    companion object {
        @JvmStatic
        fun createGameField(collection: List<List<Cell>>): GameField =
            GameField(collection.map { it.toImmutableList() }.toImmutableList())
    }
}

fun generateGameField(gameMode: GameMode, random: Random = Random.Default): GameField {
    val minesCords = buildSet {
        while (size < gameMode.minesCount) {
            createPoint(
                x = random.nextInt(from = 0, until = gameMode.fieldWidth),
                y = random.nextInt(from = 0, until = gameMode.fieldHeight)
            ).let { add(it) }
        }
    }

    return (0 until gameMode.fieldHeight).map { x ->
        (0 until gameMode.fieldWidth).map { y ->
            val p = createPoint(x, y)
            Cell(
                state = Cell.CellState.CLOSED,
                value = if (p in minesCords) {
                    Cell.CellValue.Mine
                } else {
                    val n = calculateMinesAround(p, minesCords)
                    if (n > 0) {
                        Cell.CellValue.Value(n)
                    } else {
                        Cell.CellValue.Empty
                    }
                }
            )
        }
    }.let { GameField.createGameField(it) }
}

fun getMinesCount(gameMode: GameMode): Int =
    when (gameMode) {
        GameMode.Beginner -> 10
        GameMode.Expert -> 40
        GameMode.Intermediate -> 99
        is GameMode.Custom -> gameMode.minesCount
    }

fun openCell(gameState: GameState, x: Int, y: Int): GameState {
    return gameState.run {
        copy(gameField = gameField.updateCell(x, y) { cell ->
            val newState = when(cell.state) {
                Cell.CellState.OPEN -> Cell.CellState.CLOSED
                else -> Cell.CellState.OPEN
            }
            cell.copy(state = newState)
        })
    }
}

fun markCell(gameState: GameState, x: Int, y: Int): GameState =
    gameState.run {
        copy(gameField = gameField.updateCell(x, y) { cell ->
            val newState = when(cell.state) {
                Cell.CellState.CLOSED -> Cell.CellState.FLAGGED
                Cell.CellState.FLAGGED -> Cell.CellState.CLOSED
                else -> throw IllegalStateException("We try to mark cell in ${cell.state}")
            }
            cell.copy(state = newState)
        })
    }