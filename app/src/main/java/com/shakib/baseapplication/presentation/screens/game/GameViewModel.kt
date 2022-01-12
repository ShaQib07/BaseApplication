package com.shakib.baseapplication.presentation.screens.game

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.room.game.GameDao
import com.shakib.baseapplication.domain.FetchFavGameListUseCase
import com.shakib.baseapplication.domain.FetchGameListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    fetchGameListUseCase: FetchGameListUseCase,
    private val fetchFavGameListUseCase: FetchFavGameListUseCase,
    private val gameDao: GameDao
) : BaseViewModel() {

    val paginatedGames = fetchGameListUseCase.fetchGamesPaginated().cachedIn(viewModelScope)

    fun fetchFavoriteGames() = fetchFavGameListUseCase.fetchFavGameList().asLiveData()

    fun addToFavorite(game: Game?) = viewModelScope.launch { game?.let { gameDao.insertGame(it) } }

    fun removeFromFavorite(game: Game?) =
        viewModelScope.launch { game?.let { gameDao.deleteGame(it) } }

    override fun onClear() {}
}