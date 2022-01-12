package com.shakib.baseapplication.presentation.screens.favorite

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.room.game.GameDao
import com.shakib.baseapplication.domain.FetchFavGameListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val fetchFavGameListUseCase: FetchFavGameListUseCase,
    private val gameDao: GameDao
) : BaseViewModel() {

    fun fetchFavoriteGames() = fetchFavGameListUseCase.fetchFavGameList().asLiveData()

    fun removeFromFavorite(game: Game?) =
        viewModelScope.launch { game?.let { gameDao.deleteGame(it) } }

    override fun onClear() {}

}