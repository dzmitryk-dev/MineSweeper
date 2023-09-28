package model

import currentTimeMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

interface GameModel {
    val gameState: Flow<GameState>

    fun cellClicked(x: Int, y: Int)
    fun cellMarked(x: Int, y: Int)
}

class GameModelImpl(
    gameMode: GameMode,
    random: Random = Random(currentTimeMillis())
) : GameModel {

    private val _mutableGameStateFlow = MutableStateFlow(
        GameState(
            isActive = false,
            gameField = generateGameField(gameMode, random),
            flagsCount = 40
        )
    )

    override val gameState: StateFlow<GameState>
        get() = _mutableGameStateFlow

    override fun cellClicked(x: Int, y: Int) {
        TODO("Not yet implemented")
    }

    override fun cellMarked(x: Int, y: Int) {
        TODO("Not yet implemented")
    }
}