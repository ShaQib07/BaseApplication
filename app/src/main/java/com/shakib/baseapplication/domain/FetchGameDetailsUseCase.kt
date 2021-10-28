package com.shakib.baseapplication.domain

import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.model.ScreenshotResponse
import com.shakib.baseapplication.data.repository.GameDetailsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGameDetailsUseCase @Inject constructor(private val gameDetailsRepo: GameDetailsRepo) {
    suspend fun fetchGameDetails(gameId: Int): Flow<Game> =
        gameDetailsRepo.fetchGameDetails(gameId)

    suspend fun fetchGameScreenShots(gameId: Int): Flow<ScreenshotResponse> =
        gameDetailsRepo.fetchGameScreenShots(gameId)
}