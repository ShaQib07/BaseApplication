package com.shakib.baseapplication.presentation.screens.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.extensions.printErrorLog
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.room.GameDao
import com.shakib.baseapplication.domain.FetchFavGameListUseCase
import com.shakib.baseapplication.domain.FetchGameListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val fetchGameListUseCase: FetchGameListUseCase,
    private val fetchFavGameListUseCase: FetchFavGameListUseCase,
    private val gameDao: GameDao
) : BaseViewModel() {

    val gameListLiveData by lazy { MutableLiveData<Resource<List<Game>>>() }
    var favGameList: List<Game> = listOf()
    fun fetchGameList() {
        viewModelScope.launch {
            gameListLiveData.value = Resource.Loading()
            fetchFavGameList()
            fetchGameListUseCase.fetchGameList()
                .catch { gameListLiveData.value = Resource.Error(it) }
                .collect { gameListLiveData.value = Resource.Success(it.results) }
        }
    }

    private suspend fun fetchFavGameList() =
        fetchFavGameListUseCase.fetchFavGameList()
            .catch { printErrorLog(it.message.toString()) }
            .collect { favGameList = it }


    fun addToFavorite(game: Game?) = viewModelScope.launch { game?.let { gameDao.insertGame(it) } }

    fun removeFromFavorite(game: Game?) =
        viewModelScope.launch { game?.let { gameDao.deleteGame(it) } }

    override fun onClear() {}
}