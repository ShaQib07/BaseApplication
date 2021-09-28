package com.shakib.baseapplication.presentation.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.extensions.printInfoLog
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.room.GameDao
import com.shakib.baseapplication.domain.FetchGameListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val fetchGameListUseCase: FetchGameListUseCase,
    private val gameDao: GameDao
) : BaseViewModel() {

    val gameListLiveData by lazy { MutableLiveData<Resource<List<Game>>>() }

    fun fetchGameList() {
        printInfoLog("fetchGameList called")
        viewModelScope.launch {
            gameListLiveData.value = Resource.Loading()
            fetchGameListUseCase.fetchGameList()
                .catch {
                    gameListLiveData.value = Resource.Error(it)
                }
                .collect {
                    gameListLiveData.value = Resource.Success(it.results)
                }
        }
    }

    suspend fun fetchFavGameList() = gameDao.fetchFavGameList()

    fun addToFavorite(game: Game?) = viewModelScope.launch { game?.let { gameDao.insertGame(it) } }

    fun removeFromFavorite(game: Game?) = viewModelScope.launch { game?.let { gameDao.deleteGame(it) } }

    override fun onClear() {}
}