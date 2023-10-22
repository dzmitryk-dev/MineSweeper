package model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class GameState(
    val gameStatus: GameStatus,
    val gameField: GameField,
    val flagsCount: Int,
) {
    enum class GameStatus {
        NOT_STARTED, IN_PROGRESS, GAME_OVER
    }

}

data class Cell(
    val state: CellState,
    val value: CellValue,
    val isClicked: Boolean = false
) {

    val isOpen: Boolean
        get() = state == CellState.OPEN

    val isMine: Boolean
        get() = value == CellValue.Mine

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
) : List<List<Cell>> by _field {

    internal fun updateField(updateFunc: MutableList<MutableList<Cell>>.() -> Unit): GameField {
        val mutableField = _field.map {
            it.toMutableList()
        }.toMutableList()

        mutableField.updateFunc()

        return createGameField(mutableField)
    }

    fun markCell(x: Int, y: Int): GameField {
        return updateField {
            val cell = this[x][y]
            val newState = when (cell.state) {
                Cell.CellState.CLOSED -> Cell.CellState.FLAGGED
                Cell.CellState.FLAGGED -> Cell.CellState.CLOSED
                else -> throw IllegalStateException("We try to mark cell in ${cell.state}")
            }
            this[x][y] = cell.copy(state = newState)
        }
    }

    fun openCell(x: Int, y: Int): GameField {
        return updateField {
            val cell = this[x][y]
            this[x][y] = cell.copy(isClicked = true)
            if (cell.value != Cell.CellValue.Empty) {
                this[x][y] = this[x][y].copy(state = Cell.CellState.OPEN)
            } else  {
                tailrec fun openAllAffectedCells(
                    field: MutableList<MutableList<Cell>>,
                    points: Set<Point>
                ) {
                    val newPoints = mutableSetOf<Point>()
                    points.forEach { (x, y) ->
                        val c = field[x][y]
                        if (!c.isOpen) {
                            field[x][y] = c.copy(state = Cell.CellState.OPEN)
                            if (c.value == Cell.CellValue.Empty) {
                                (max(y-1,0)..min(y+1, field.lastIndex)).flatMap { y1 ->
                                    (max(x-1, 0)..min(x+1, field[y1].lastIndex)).map { x1 ->
                                        createPoint(x1, y1)
                                    }
                                }.filterNot { (x1, y1) -> x1 == x && y1 == y }
                                .toSet().let { newPoints.addAll(it) }
                            }
                        }
                    }
                    if (newPoints.isEmpty()) return
                    openAllAffectedCells(field, newPoints)
                }
                openAllAffectedCells(this, setOf(createPoint(x, y)))
            }
        }
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

    return generateGameFieldInternal(minesCords, gameMode.fieldWidth, gameMode.fieldHeight)
}

internal fun generateGameFieldInternal(minesCords: Set<Point>, fieldWidth: Int, fieldHeight: Int): GameField {
    return (0 until fieldHeight).map { x ->
        (0 until fieldWidth).map { y ->
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
    val newGameField = gameState.gameField.openCell(x, y)
    return gameState.copy(
        gameField = newGameField
    ).let { checkGameState(it) }
}

fun markCell(gameState: GameState, x: Int, y: Int): GameState =
    gameState.run {
        copy(gameField = gameField.markCell(x, y))
    }.let { checkGameState(it) }


internal fun checkGameState(gameState: GameState): GameState {
    val initialState = if (gameState.gameStatus == GameState.GameStatus.NOT_STARTED) {
        gameState.copy(gameStatus = GameState.GameStatus.IN_PROGRESS)
    } else {
        gameState
    }

    // Game is over if mine is opened
    val isGameOver = initialState.gameField.flatten().any { cell -> cell.isOpen && cell.isMine }

    return if (isGameOver) {
        initialState.copy(
            gameStatus = GameState.GameStatus.GAME_OVER,
            gameField = GameField.createGameField(initialState.gameField.map {
                it.map { c ->
                    if (c.isMine) {
                        c.copy(state = Cell.CellState.OPEN)
                    } else c
                }
            })
        )
    } else {
        initialState
    }
}