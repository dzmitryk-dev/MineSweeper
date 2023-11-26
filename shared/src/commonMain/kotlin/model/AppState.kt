package model

sealed class AppState {
    data object SelectScreen : AppState()
    data class GameScreen(
        val gameModel: GameModel
    ): AppState()
}