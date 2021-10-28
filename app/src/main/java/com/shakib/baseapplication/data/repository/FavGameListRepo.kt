package com.shakib.baseapplication.data.repository

import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.room.GameDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavGameListRepo @Inject constructor(private val gameDao: GameDao) {
    suspend fun fetchFavGameList(): Flow<List<Game>> =
        flow { emit(gameDao.fetchFavGameList()) }
}