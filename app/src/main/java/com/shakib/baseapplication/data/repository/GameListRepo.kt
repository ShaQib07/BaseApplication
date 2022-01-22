package com.shakib.baseapplication.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakib.baseapplication.common.utils.HelperConstants.NETWORK_PAGE_SIZE
import com.shakib.baseapplication.common.utils.HelperConstants.STARTING_INDEX
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.model.GameResponse
import com.shakib.baseapplication.data.network.GameApi
import com.shakib.baseapplication.data.repository.paging.GamePagingSource
import com.shakib.baseapplication.data.repository.paging.GameRemoteMediator
import com.shakib.baseapplication.data.room.game.GameDao
import com.shakib.baseapplication.data.room.game.PageDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GameListRepo @Inject constructor(
    private val gameApi: GameApi,
    private val gameDao: GameDao,
    private val pageDao: PageDao
) {
    suspend fun fetchGameList(): Flow<GameResponse> =
        flow { emit(gameApi.getGamesPageByPage(STARTING_INDEX, NETWORK_PAGE_SIZE)) }

    // Uses paging source
    fun fetchGamesPaginated(): Flow<PagingData<Game>> = Pager(
        PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        )
    ) { GamePagingSource(gameApi) }.flow

    // Uses remote mediator
    /*fun fetchGamesPaginated(): Flow<PagingData<Game>> = Pager(
        PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = true
        ),
        remoteMediator = GameRemoteMediator(gameApi, gameDao, pageDao),
        pagingSourceFactory = { gameDao.fetchGameList() }
    ).flow*/
}