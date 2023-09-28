package model

import kotlin.random.Random

data class GameState(
    val isActive: Boolean,
    val gameField: List<List<Cell>>,
    val flagsCount: Int,
) {
    companion object {
        @JvmStatic
        val EMPTY = GameState(
            isActive = false,
            gameField = emptyList(),
            flagsCount = 0
        )
    }
}

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
        (p.x-1..p.x+1).forEach { x ->
            (p.y-1..p.y+1).forEach { y ->
                if (!(x == p.x && y == p.y)) {
                    add(createPoint(x, y))
                }
            }
        }
    }
    return pointsToCheck.count { it in minesCords }
}

fun generateGameField(gameMode: GameMode, random: Random = Random.Default): List<List<Cell>> {
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
            Cell(state = Cell.CellState.CLOSED,
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
    }
}