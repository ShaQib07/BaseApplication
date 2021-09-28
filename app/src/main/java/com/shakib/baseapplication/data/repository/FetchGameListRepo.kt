package com.shakib.baseapplication.data.repository

import com.shakib.baseapplication.data.model.GameResponse
import com.shakib.baseapplication.data.network.GameApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchGameListRepo @Inject constructor(private val gameApi: GameApi) {
    suspend fun fetchGameList(): Flow<GameResponse> =
        flow { emit(gameApi.getGamesPageByPage("2020-01-01,2020-12-31", "-added", 1)) }
}