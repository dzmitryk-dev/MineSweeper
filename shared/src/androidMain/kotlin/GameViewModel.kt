import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import model.GameMode
import model.GameModel
import model.GameModelImpl
import kotlin.random.Random

class GameViewModel(
    gameModelImpl: GameModel
) : ViewModel(), GameModel by gameModelImpl{

    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
            ): T {
                return GameViewModel(
                    GameModelImpl(GameMode.Beginner, Random(currentTimeMillis()))
                ) as T
            }
        }
    }
}