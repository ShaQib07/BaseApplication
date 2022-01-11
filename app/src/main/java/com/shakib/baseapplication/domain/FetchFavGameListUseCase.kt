package com.shakib.baseapplication.domain

import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.repository.FavGameListRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchFavGameListUseCase @Inject constructor(private val gameListRepo: FavGameListRepo) {
    fun fetchFavGameList(): Flow<List<Game>> = gameListRepo.fetchFavGameList()
}