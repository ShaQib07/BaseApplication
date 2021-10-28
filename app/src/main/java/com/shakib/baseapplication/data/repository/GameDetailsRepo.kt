package com.shakib.baseapplication.data.repository

import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.model.ScreenshotResponse
import com.shakib.baseapplication.data.network.GameApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameDetailsRepo @Inject constructor(private val gameApi: GameApi) {
    suspend fun fetchGameDetails(gameId: Int): Flow<Game> =
        flow { emit(gameApi.getGameDetailsById(gameId)) }

    suspend fun fetchGameScreenShots(gameId: Int): Flow<ScreenshotResponse> =
        flow { emit(gameApi.getGameScreenshotById(gameId)) }
}