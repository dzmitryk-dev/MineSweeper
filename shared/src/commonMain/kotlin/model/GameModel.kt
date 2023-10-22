package model

import currentTimeMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

interface GameModel {
    val gameState: StateFlow<GameState>

    fun cellClicked(x: Int, y: Int)
    fun cellMarked(x: Int, y: Int)
    fun restart()
}

class GameModelImpl(
    private val gameMode: GameMode,
    private val random: Random = Random(currentTimeMillis())
) : GameModel {

    private val _mutableGameStateFlow = MutableStateFlow(
        GameState(
            gameStatus = GameState.GameStatus.NOT_STARTED,
            gameField = generateGameField(gameMode, random),
            flagsCount = getMinesCount(gameMode)
        )
    )

    override val gameState: StateFlow<GameState>
        get() = _mutableGameStateFlow

    override fun cellClicked(x: Int, y: Int) {
        _mutableGameStateFlow.update { oldState -> openCell(oldState, x, y) }
    }

    override fun cellMarked(x: Int, y: Int) {
        _mutableGameStateFlow.update { oldState -> markCell(oldState, x, y) }
    }

    override fun restart() {
        _mutableGameStateFlow.update {
            GameState(
                gameStatus = GameState.GameStatus.NOT_STARTED,
                gameField = generateGameField(gameMode, random),
                flagsCount = getMinesCount(gameMode)
            )
        }
    }
}