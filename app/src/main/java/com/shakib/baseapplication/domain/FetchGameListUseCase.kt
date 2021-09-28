package com.shakib.baseapplication.domain

import com.shakib.baseapplication.data.model.GameResponse
import com.shakib.baseapplication.data.repository.FetchGameListRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGameListUseCase @Inject constructor(
    private val fetchGameListRepo: FetchGameListRepo
) {
    suspend fun fetchGameList(): Flow<GameResponse> =
        fetchGameListRepo.fetchGameList()
}