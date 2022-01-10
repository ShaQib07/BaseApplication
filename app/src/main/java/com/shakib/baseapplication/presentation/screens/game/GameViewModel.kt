package com.shakib.baseapplication.presentation.screens.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.room.game.GameDao
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

    val favGameListLiveData by lazy { MutableLiveData<Resource<List<Game>>>() }

    fun fetchFavGameList() {
        viewModelScope.launch {
            favGameListLiveData.value = Resource.Loading()
            fetchFavGameListUseCase.fetchFavGameList()
                .catch { favGameListLiveData.value = Resource.Error(it) }
                .collect { favGameListLiveData.value = Resource.Success(it) }
        }
    }

    fun fetchGamesPaginated() = fetchGameListUseCase.fetchGamesPaginated().cachedIn(viewModelScope)

    fun addToFavorite(game: Game?) = viewModelScope.launch { game?.let { gameDao.insertGame(it) } }

    fun removeFromFavorite(game: Game?) =
        viewModelScope.launch { game?.let { gameDao.deleteGame(it) } }

    override fun onClear() {}
}