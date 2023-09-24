package model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface GameModel {
    val gameState: Flow<GameState>
}

class GameModelImpl(
    gameMode: GameMode
) : GameModel {

    private val _mutableGameStateFlow = MutableStateFlow(GameState.EMPTY)
    override val gameState: StateFlow<GameState>
        get() = _mutableGameStateFlow

    private fun generate() {

    }
}