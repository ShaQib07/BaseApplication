package com.shakib.baseapplication.domain

import com.shakib.baseapplication.data.model.GameResponse
import com.shakib.baseapplication.data.repository.GameListRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGameListUseCase @Inject constructor(private val gameListRepo: GameListRepo) {
    suspend fun fetchGameList(): Flow<GameResponse> = gameListRepo.fetchGameList()
}